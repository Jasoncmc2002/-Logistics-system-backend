package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.Category;
import com.example.distributionmanagementcenter.entity.Constans;
import com.example.distributionmanagementcenter.entity.Good;
import com.example.distributionmanagementcenter.entity.HttpResponseEntity;
import com.example.distributionmanagementcenter.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/distribute/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;



    @GetMapping(value = "/{id}")
    public HttpResponseEntity<Category> getById(@PathVariable("id") String id) {
        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
        try {
            Category category=categoryService.getById(id);
            if(category!=null)
            {
                httpResponseEntity.setData(category);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("getById ID查找种类>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
//确定插入值唯一
    @PostMapping(value = "/create")
    public HttpResponseEntity<Category> create(@RequestBody Category params) {
        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
        try {
            int flag=0;
            List<Category> categoryList = categoryService.list();
            for(Category category:categoryList){
                if(category.getFName().equals(params.getFName())&&category.getSName().equals(params.getSName())){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                categoryService.save(params);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }
            else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("create 新建种类>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @PostMapping(value = "/delete/{id}")
    public HttpResponseEntity<Category> delete(@PathVariable("id") String id) {

        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
        try {
            boolean flag=categoryService.removeById(id);
            if(flag)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("delete 删除种类>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    @PostMapping(value = "/update")
    public HttpResponseEntity<Category> update(@RequestBody Category params) {

        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
        try{
            boolean flag=categoryService.updateById(params);
            if(flag)
            {

                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("update 更新种类>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
