package com.example.distributionmanagementcenter.controller;

import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.Category;
import com.example.distributionmanagementcenter.entity.Constans;
import com.example.distributionmanagementcenter.entity.HttpResponseEntity;
import com.example.distributionmanagementcenter.service.BuyService;
import com.example.distributionmanagementcenter.service.CategoryService;
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
@RequestMapping("/buy")
public class BuyController {

    private final Logger logger = LoggerFactory.getLogger(BuyController.class);
    @Autowired
    private BuyService buyService;



    @GetMapping(value = "/{id}")
    public HttpResponseEntity<Buy> getById(@PathVariable("id") String id) {
        HttpResponseEntity<Buy> httpResponseEntity = new HttpResponseEntity<Buy>();
        try {
           Buy buy=buyService.getById(id);
            if(buy!=null)
            {
                httpResponseEntity.setData(buy);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("getById ID查找订单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
////确定插入值唯一
//    @PostMapping(value = "/create")
//    public HttpResponseEntity<Category> create(@RequestBody Category params) {
//        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
//        try {
//            int flag=0;
//            List<Category> categoryList = categoryService.list();
//            for(Category category:categoryList){
//                if(category.getfName().equals(params.getfName())&&category.getsName().equals(params.getsName())){
//                    flag=1;
//                    break;
//                }
//            }
//            if(flag==0){
//                categoryService.save(params);
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//            }
//            else
//            {
//                httpResponseEntity.setCode(Constans.EXIST_CODE);
//                httpResponseEntity.setMessage(Constans.ADD_FAIL);
//            }
//
//        } catch (Exception e) {
//            logger.info("create 新建种类>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
//
//
//    @PostMapping(value = "/delete/{id}")
//    public HttpResponseEntity<Buy> delete(@PathVariable("id") String id) {
//
//        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
//        try {
//            boolean flag=categoryService.removeById(id);
//            if(flag)
//            {
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//            }else
//            {
//                httpResponseEntity.setCode(Constans.EXIST_CODE);
//                httpResponseEntity.setMessage(Constans.ADD_FAIL);
//            }
//
//        } catch (Exception e) {
//            logger.info("delete 删除种类>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
//
//
//    @PostMapping(value = "/update")
//    public HttpResponseEntity<Category> update(@RequestBody Category params) {
//
//        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
//        try{
//            boolean flag=categoryService.updateById(params);
//            if(flag)
//            {
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//            }else
//            {
//                httpResponseEntity.setCode(Constans.EXIST_CODE);
//                httpResponseEntity.setMessage(Constans.ADD_FAIL);
//            }
//
//        } catch (Exception e) {
//            logger.info("update 更新种类>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
}
