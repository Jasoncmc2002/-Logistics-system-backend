package com.example.substationmanagementcenter.sevice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.entity.Receipt;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.feign.FeignApi;
import com.example.substationmanagementcenter.mapper.ReceiptMapper;
import com.example.substationmanagementcenter.sevice.ReceiptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回执单 服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Service
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private FeignApi feignApi;

    @Override
    public int updatebyId(Receipt receipt) {
        return receiptMapper.updateById(receipt);
    }

    @Override
    public int receiptEntry(Map<String, Object> map) {
        int res = 0;


        Receipt receipt = new Receipt();

//        receipt.setGoodPrice(Double.parseDouble(String.valueOf(map.get("goodPrice"))));
        receipt.setTaskStatus(String.valueOf(map.get("taskStatus")));
        receipt.setAddress(String.valueOf(map.get("address")));
        System.out.println("1");
        receipt.setCustomerSatis(String.valueOf(map.get("customerSatis")));
        receipt.setRemark(String.valueOf(map.get("remark")));
        receipt.setNumber(String.valueOf(map.get("number")));
        System.out.println("2");
        receipt.setPrice(Double.parseDouble(String.valueOf(map.get("price"))));
        receipt.setReplyClass(Integer.valueOf(String.valueOf(map.get("replyClass"))));
        receipt.setMobilephone(String.valueOf(map.get("mobilePhone")));
        System.out.println("3");
        receipt.setSubstation(String.valueOf(map.get("substation")));
        receipt.setCustomerName(String.valueOf(map.get("customerName")));
        receipt.setTaskType(String.valueOf(map.get("taskType")));
        receipt.setGoodName(String.valueOf(map.get("goodName")));
        receipt.setGoodSum(Long.valueOf(String.valueOf(map.get("goodSum"))));
        receipt.setPostman(String.valueOf(map.get("postman")));

        HttpResponseEntity resUserNumber = feignApi.getUseNumber(map);
        receipt.setInvoiceNumber(Long.valueOf(String.valueOf(resUserNumber.getData())));

        res = receiptMapper.insert(receipt);


        //改订单状态
        Map map1 =new HashMap<String,Object>();
        map1.put("id",map.get("id"));
        map1.put("task_status","已完成");
        map1.put("order_status","已完成");
        HttpResponseEntity res2= feignApi.changeTaskOrderType(map1);

        return (int)res2.getData();
    }

    @Override
    public PageInfo getList(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        List<Receipt> records= receiptMapper.selectList(null);
//        List<Receipt> records1 = new ArrayList<Receipt>();
//        for(Receipt good :records){
//            int flag=0;
//            for(Receipt good1:records1){
//                if(good1.getCustomerSatis()==good.getCustomerSatis()){
//                    good1.setNumber(good1.getNumber()+1);
//                    flag=1;
//                }
//            }
//            if(flag==0){
//                records1.add(good);
//            }
//        }
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }
}
