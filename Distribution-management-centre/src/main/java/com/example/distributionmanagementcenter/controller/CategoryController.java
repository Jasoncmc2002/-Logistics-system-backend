package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.Category;
import com.example.distributionmanagementcenter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Category>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Category> aPage = categoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }
//确定插入值唯一
    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Category params) {
        int flag=0;
        System.out.println(params);
        List<Category> categoryList = categoryService.list();
        for(Category category:categoryList){
            if(category.getfName().equals(params.getfName())&&category.getsName().equals(params.getsName())){
                flag=1;
                break;
            }
        }
        if(flag==0){
            categoryService.save(params);
            return new ResponseEntity<>("created successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("duplicated creation!", HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        categoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Category params) {
        categoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
