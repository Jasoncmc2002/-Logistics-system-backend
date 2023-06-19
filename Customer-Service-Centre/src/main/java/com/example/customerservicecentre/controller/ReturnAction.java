package com.example.customerservicecentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 退订 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/return")
public class ReturnAction {


    @Autowired
    private ReturnService returnService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Return>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Return> aPage = returnService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Return> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(returnService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Return params) {
        returnService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        returnService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Return params) {
        returnService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
