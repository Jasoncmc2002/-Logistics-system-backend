package com.example.financialmanagement.controller;

import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.common.Constans;
import com.example.financialmanagement.service.impl.FinancialServiceImpl;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Controller
@RequestMapping("/financial")
public class FinancialAction {

    private final Logger logger = LoggerFactory.getLogger(FinancialAction.class);

    private FinancialServiceImpl financialService =new FinancialServiceImpl();

    @RequestMapping(value = "/SettlementSupply",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity SettlementSupply(@RequestBody Map<String,Object> mpa) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=financialService.settlementSupply(mpa);
            if(res==1)
            {
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





}
