package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.CentralStation;
import com.example.distributionmanagementcenter.entity.Constans;
import com.example.distributionmanagementcenter.entity.HttpResponseEntity;
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
@Controller
@RequestMapping("/central-station")
public class CentralstationController {

    private final Logger logger = LoggerFactory.getLogger(CentralstationController.class);
    @Autowired
    private CentralstationService centralstationService;
    @Autowired
    private BuyService buyService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<CentralStation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<CentralStation> aPage = centralstationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CentralStation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(centralstationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody CentralStation params) {
        centralstationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        centralstationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody CentralStation params) {
        centralstationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
    //缺货检查
    @RequestMapping(value = "/check/{id}",method = RequestMethod.GET, headers = "Accept"
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
    @RequestMapping(value = "/checkAll",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity checkAllVacancy() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            List<CentralStation> centralStationList=centralstationService.list();
            HashMap<String,Object> responseContent=new HashMap<String,Object>();
            for(CentralStation centralStation:centralStationList){
                responseContent.put("Good",centralStation);
                if(centralStation.getWaitAllo()<=centralStation.getWarn()){
                    responseContent.put("LackNum",centralStation.getWarn()-centralStation.getWaitAllo());
                }
                else{
                    responseContent.put("LackNum",0);
                }
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
    //新增进货单
    @RequestMapping(value = "/addBuy/{id}",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity addBuy(@PathVariable("id") int id,@RequestBody Buy param) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            CentralStation centralStation=centralstationService.getById(id);
            if(param.getNumber()<=(centralStation.getMax()-centralStation.getWaitAllo())){
                buyService.save(param);
                httpResponseEntity.setData("Success");
            }else{
                httpResponseEntity.setData("Invalid input number");
            }

            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        }catch(Exception e){
            logger.info("addBuy 新增进货单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }
}
