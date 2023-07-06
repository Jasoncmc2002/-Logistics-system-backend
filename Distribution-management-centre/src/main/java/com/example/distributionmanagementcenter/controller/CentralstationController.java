package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.*;
import com.example.distributionmanagementcenter.service.BuyService;
import com.example.distributionmanagementcenter.service.CentralstationService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中心库房存量  前端控制器
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/distribute/central-station")
public class CentralstationController {

    private final Logger logger = LoggerFactory.getLogger(CentralstationController.class);
    @Autowired
    private CentralstationService centralstationService;
    @Autowired
    private BuyService buyService;


    @PostMapping(value = "/{id}")
    public HttpResponseEntity<CentralStation> getById(@PathVariable("id") String id) {
        HttpResponseEntity<CentralStation> httpResponseEntity = new HttpResponseEntity<CentralStation>();
        try {
            CentralStation centralStation=centralstationService.getById(id);
            if(centralStation!=null)
            {
                httpResponseEntity.setData(centralStation);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("getById ID查找中心库房库存量>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
//    @PostMapping(value = "/getList")
//    public HttpResponseEntity getList(@RequestBody Map<String, Object> map) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        try {
//            PageInfo pageInfo=centralstationService.getList(map);
//                httpResponseEntity.setData(pageInfo);
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//
//        } catch (Exception e) {
//            logger.info("查找商品列表" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
    @PostMapping(value = "/getListByCondition")
    public HttpResponseEntity getListByCondition(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo=centralstationService.getListByCondition(map);
//            System.out.println(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("根据条件查找商品列表" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    @PostMapping(value = "/create")
    public HttpResponseEntity<CentralStation> create(@RequestBody CentralStation params) {
        HttpResponseEntity<CentralStation> httpResponseEntity = new HttpResponseEntity<CentralStation>();
        try {
            boolean flag=centralstationService.save(params);
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
            logger.info("create 新建中心库房库存量>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    @PostMapping(value = "/delete/{id}")
    public HttpResponseEntity<CentralStation> delete(@PathVariable("id") String id) {

        HttpResponseEntity<CentralStation> httpResponseEntity = new HttpResponseEntity<CentralStation>();
        try {
            boolean flag=centralstationService.removeById(id);
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
            logger.info("delete 删除中心库房库存量>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @PostMapping("/update")
    public HttpResponseEntity<CentralStation> update(@RequestBody CentralStation params) {

        HttpResponseEntity<CentralStation> httpResponseEntity = new HttpResponseEntity<CentralStation>();
        try {
            boolean flag=centralstationService.updateById(params);
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
            logger.info("update 更新中心库房库存量>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    @PostMapping("/updateList")
    public HttpResponseEntity<CentralStation> updateList(@RequestBody Map<String, Object> map) {

        HttpResponseEntity<CentralStation> httpResponseEntity = new HttpResponseEntity<CentralStation>();
        try {
            centralstationService.updateList(map);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("列表更新>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    //缺货检查
    @RequestMapping(value = "/check/{id}",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity checkVacancyById(@PathVariable("id")int id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            CentralStation centralStation=centralstationService.getById(id);
            HashMap<String,Object> responseContent=new HashMap<String,Object>();
            if(centralStation.getWaitAllo()<=centralStation.getWarn()){

                responseContent.put("LackNum",centralStation.getWarn()-centralStation.getWaitAllo());
            }
            else{
                responseContent.put("LackNum",0);
            }
            httpResponseEntity.setData(responseContent);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        }catch(Exception e){
            logger.info("checkVacancy 缺货查询>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }
    //缺货检查（全部）
    @RequestMapping(value = "/checkAll",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity checkAllVacancy(@RequestBody Map<String, Object> map) {
//        DateTimeFormatter df5 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//        System.out.println(df5.format(ZonedDateTime.now()));
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            PageInfo pageInfo=centralstationService.getListByCondition(map);

            httpResponseEntity.setData(map);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        }catch(Exception e){
            logger.info("checkVacancy 缺货查询>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    //新增进货单
    @RequestMapping(value = "/addBuy/{id}",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity addBuy(@PathVariable("id") int id,@RequestBody Buy param) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            CentralStation centralStation=centralstationService.getById(id);
            if(param.getNumber()<=(centralStation.getMax()-centralStation.getWaitAllo())){
                param.setType(1);
                buyService.save(param);
                httpResponseEntity.setData("Success");
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else{
                httpResponseEntity.setData("Invalid input number");
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }

        }catch(Exception e){
            logger.info("addBuy 新增进货单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }

    @RequestMapping(value = "/addBuyList",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity addBuyList(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            centralstationService.addBuyList(map);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        }catch(Exception e){
            logger.info("addBuyList 新增进货单列表>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }
    //进货登记
    @RequestMapping(value = "/registerBuy/{id}",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity registerBuy(@PathVariable("id") int id,@RequestBody Buy param) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            CentralStation centralStation=centralstationService.getById(id);
            if(param.getNumber()<=(centralStation.getMax()-centralStation.getWaitAllo())){
                param.setType(2);
                buyService.save(param);
                httpResponseEntity.setData("Success");
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else{
                httpResponseEntity.setData("Invalid input number");
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
            }

        }catch(Exception e){
            logger.info("registerBuy 进货登记>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

}
