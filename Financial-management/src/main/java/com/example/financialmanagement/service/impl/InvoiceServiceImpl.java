package com.example.financialmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.financialmanagement.mapper.InvoiceMapper;
import com.example.financialmanagement.service.InvoiceService;
import org.springframework.stereotype.Service;
import com.example.financialmanagement.entity.*;
/**
 * <p>
 * 发票 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements
    InvoiceService {

}
