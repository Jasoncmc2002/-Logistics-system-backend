package com.example.dispatchcentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dispatchcentre.entity.Allocation;
import com.example.dispatchcentre.entity.Task;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * <p>
 * 商品调拨 服务类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
public interface AllocationService extends IService<Allocation> {
  int insert(Allocation allocation);
  int updatebyId(Map<String, Object> map);
  Allocation selectbyId(Long id);
  PageInfo selectAll(Map<String,Object> map);
  PageInfo searchbykey(Map<String,Object> map);
}
