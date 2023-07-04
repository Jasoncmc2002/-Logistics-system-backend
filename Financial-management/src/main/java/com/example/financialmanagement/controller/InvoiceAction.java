package com.example.financialmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.common.Constans;
import com.example.financialmanagement.entity.Invoice;
import com.example.financialmanagement.entity.vo.ResultStation;
import com.example.financialmanagement.service.InvoiceService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 发票 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@RestController
@RequestMapping("/financial")
public class InvoiceAction {

    private final Logger logger = LoggerFactory.getLogger(FinancialAction.class);
    @Autowired
    private InvoiceService invoiceService;
    @RequestMapping(value = "/addInvoice",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addInvoice(@RequestBody Invoice invoice) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println(invoice);
        try {
            int res=invoiceService.addInvoice(invoice);
            if(res!=0)
            {
                httpResponseEntity.setData(res);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("invoiceService 添加发票>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/addUseInvoice",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addUseInvoice(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println(map);
        try {
            int res=invoiceService.addUseInvoice(map);
            if(res!=0)
            {
                httpResponseEntity.setData(res);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("addUseInvoice 添加使用的发票>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/changeUseInvoice",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity changeUseInvoice(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println(map);
        try {
            int res=invoiceService.addUseInvoice(map);
            if(res!=0)
            {
                httpResponseEntity.setData(res);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("addUseInvoice 添加使用的发票>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
