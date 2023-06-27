package com.example.distributionmanagementcenter.controller;

import com.example.distributionmanagementcenter.entity.FirstCategory;
import com.example.distributionmanagementcenter.entity.Constans;
import com.example.distributionmanagementcenter.entity.HttpResponseEntity;
import com.example.distributionmanagementcenter.service.FirstCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/distribute/firstcategory")
public class FirstCategoryController {

    private final Logger logger = LoggerFactory.getLogger(FirstCategoryController.class);
    @Autowired
    private FirstCategoryService firstCategoryService;



    @GetMapping(value = "/{id}")
    public HttpResponseEntity<FirstCategory> getById(@PathVariable("id") String id) {
        HttpResponseEntity<FirstCategory> httpResponseEntity = new HttpResponseEntity<FirstCategory>();
        try {
            FirstCategory firstCategory = firstCategoryService.getById(id);
            if(firstCategory !=null)
            {
                httpResponseEntity.setData(firstCategory);
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
    public HttpResponseEntity create(@RequestBody FirstCategory params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int flag=0;
            List<FirstCategory> firstCategoryList = firstCategoryService.list();
            for(FirstCategory firstCategory : firstCategoryList){
                if(firstCategory.getFName().equals(params.getFName())){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                firstCategoryService.save(params);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }
            else
            {
                httpResponseEntity.setData("插入值重复！");
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
    public HttpResponseEntity<FirstCategory> delete(@PathVariable("id") String id) {

        HttpResponseEntity<FirstCategory> httpResponseEntity = new HttpResponseEntity<FirstCategory>();
        try {
            boolean flag= firstCategoryService.removeById(id);
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
    public HttpResponseEntity<FirstCategory> update(@RequestBody FirstCategory params) {

        HttpResponseEntity<FirstCategory> httpResponseEntity = new HttpResponseEntity<FirstCategory>();
        try{
            boolean flag= firstCategoryService.updateById(params);
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