package com.example.financialmanagement.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagement.common.utils.DateUtil;
import com.example.financialmanagement.entity.Invoice;
import com.example.financialmanagement.entity.Use;
import com.example.financialmanagement.mapper.UseMapper;
import com.example.financialmanagement.service.UseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-28
 */
@Service
public class UseServiceImpl extends ServiceImpl<UseMapper, Use> implements UseService {

  private UseMapper useMapper;

  @Override
  public int addInvoice(Use use) {
    int res=useMapper.insert(use);
    return res;
  }

  @Override
  public int changeUseByid(Use use) {
    int res=useMapper.updateById(use);
    return res;
  }

  @Override
  public PageInfo select(Map<String,Object> map) throws ParseException {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    QueryWrapper<Use> queryWrapper = new QueryWrapper<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime =null;
    Date endTime = null;
    Date now= DateUtil.getCreateTime();
    Date old=sdf.parse("1993-07-01 17:54:18");
    if(!map.get("startTime").equals("")){
      startTime =sdf.parse((String) map.get("startTime"));
    }
    if(!map.get("endTime").equals("")){
      endTime =sdf.parse((String) map.get("endTime"));
    }
    // 判断name属性是否为空，如果不为空则作为查询条件
    if (!map.get("type").equals("")) {
      queryWrapper.like("type", map.get("type"));
    }
    if (startTime!=null&& endTime!=null) {
      queryWrapper.between("order_date", startTime, endTime);
    }
    else if (startTime==null&& endTime!=null) {
      queryWrapper.between("order_date", old, endTime);
    }
    else if (startTime!=null&& endTime==null) {
      queryWrapper.between("order_date", startTime, now);
    }
    if (!map.get("station").equals("")) {
      queryWrapper.eq("station", map.get("station"));
    }
    List<Use> res= useMapper.selectList(queryWrapper);

//    System.out.println(res);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }

}
