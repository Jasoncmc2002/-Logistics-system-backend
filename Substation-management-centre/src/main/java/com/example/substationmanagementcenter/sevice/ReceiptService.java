package com.example.substationmanagementcenter.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.substationmanagementcenter.entity.Receipt;

/**
 * <p>
 * 回执单 服务类
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
public interface ReceiptService extends IService<Receipt> {

    int updatebyId(Receipt receipt);

}
