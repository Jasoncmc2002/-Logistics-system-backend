package com.example.customerservicecentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.customerservicecentre.entity.Customer;
import com.example.customerservicecentre.mapper.CustomerMapper;
import com.example.customerservicecentre.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表信息 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
  @Autowired
  private CustomerMapper customerMapper;

  @Override
  public int insert(Customer customer) {
    int res= customerMapper.insert(customer);
    return res;
  }

  @Override
  public int updatebyId(Customer customer) {
    int res=customerMapper.updateById(customer);
    return res;
  }

  @Override
  public Customer selectbyId(Long id) {
    return customerMapper.selectById(id);
  }

  @Override
  public PageInfo selectAll(Map<String,Object> map) {
    PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
        Integer.valueOf((String)map.get("pageSize")));
      List<Customer> res= customerMapper.selectList(null);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }

  @Override
  public int deletebyId(Long id) {
    return customerMapper.deleteById(id);
  }
}
