package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.Supply;
import com.example.distributionmanagementcenter.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 供应商 前端控制器
 * </p>
 *
 * @author jason_cai
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/supply")
public class SupplyController {


    @Autowired
    private SupplyService supplyService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Supply>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Supply> aPage = supplyService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Supply> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(supplyService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Supply params) {
        supplyService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        supplyService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Supply params) {
        supplyService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
