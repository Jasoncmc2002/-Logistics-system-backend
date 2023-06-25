package com.example.dispatchcentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.dispatchcentre.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 商品调拨 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
@Controller
@RequestMapping("/allocation")
public class AllocationAction {


    @Autowired
    private AllocationService allocationService;





}
