package com.example.substationmanagementcenter.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.entity.Receipt;
import com.example.substationmanagementcenter.mapper.ReceiptMapper;
import com.example.substationmanagementcenter.sevice.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int updatebyId(Receipt receipt) {
        return receiptMapper.updateById(receipt);
    }
}
