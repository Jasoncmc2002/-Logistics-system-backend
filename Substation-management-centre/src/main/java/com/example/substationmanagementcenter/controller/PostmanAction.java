package com.example.substationmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.substationmanagementcenter.entity.Postman;
import com.example.substationmanagementcenter.sevice.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 配送员 前端控制器
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Controller
@RequestMapping("/postman")
public class PostmanAction {


    @Autowired
    private PostmanService postmanService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Postman>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Postman> aPage = postmanService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Postman> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(postmanService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Postman params) {
        postmanService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        postmanService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Postman params) {
        postmanService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
