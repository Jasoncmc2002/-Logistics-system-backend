package com.example.financialmanagement.controller;

import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.common.Constans;
import com.example.financialmanagement.entity.vo.ResultStation;
import com.example.financialmanagement.entity.vo.ResultSupply;
import com.example.financialmanagement.service.impl.FinancialServiceImpl;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@RestController
@RequestMapping("/financial")
public class FinancialAction {

    private final Logger logger = LoggerFactory.getLogger(FinancialAction.class);

    @Autowired
    private FinancialServiceImpl financialService;

    @RequestMapping(value = "/SettlementSupply",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity SettlementSupply(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            ResultSupply res=financialService.settlementSupply(map);
            if(res!=null)
            {
                httpResponseEntity.setData(res);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("SettlementSupply 与供货商结算>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/SettlementStation",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity SettlementStation(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            ResultStation res=financialService.settlementStation(map);
            if(res!=null)
            {
                httpResponseEntity.setData(res);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("SettlementSupply 分站缴费查询>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
