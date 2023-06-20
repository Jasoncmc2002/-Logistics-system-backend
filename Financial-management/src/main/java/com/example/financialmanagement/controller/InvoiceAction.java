package com.example.financialmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagement.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 发票 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Controller
@RequestMapping("/financial")
public class InvoiceAction {


    @Autowired
    private InvoiceService invoiceService;

}
