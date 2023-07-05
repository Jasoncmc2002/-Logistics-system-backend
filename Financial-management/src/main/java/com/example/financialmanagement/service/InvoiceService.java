package com.example.financialmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialmanagement.entity.Invoice;
import com.example.financialmanagement.entity.vo.ResultStation;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票 服务类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Service
public interface InvoiceService extends IService<Invoice> {
    int addInvoice(Invoice invoice);

  int invoiceService(Map<String, Object> map);

  int addUseInvoice(Map<String, Object> map);
}
