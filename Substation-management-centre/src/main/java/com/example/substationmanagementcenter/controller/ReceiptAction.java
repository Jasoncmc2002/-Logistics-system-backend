package com.example.substationmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.common.Constans;
import com.example.substationmanagementcenter.entity.Receipt;
import com.example.substationmanagementcenter.sevice.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 回执单 前端控制器
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@RestController
@RequestMapping("/substation/receipt")
public class ReceiptAction {


    @Autowired
    private ReceiptService receiptService;

    private final Logger logger = LoggerFactory.getLogger(TaskAction.class);

    @RequestMapping(value = "/receiptEntry",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity receiptEntry(@RequestBody Map<String,Object> map){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=receiptService.receiptEntry(map);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.UPDATE_FAIL);
            }

        } catch (Exception e) {
            logger.info("receiptEntry 回执单信息录入>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<Receipt>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Receipt> aPage = receiptService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }



    @RequestMapping(value = "/updateById",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity updateReceipt(@RequestBody Receipt receipt){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=receiptService.updatebyId(receipt);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.UPDATE_FAIL);
            }

        } catch (Exception e) {
            logger.info("updateTask 修改回执单信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/getReceiptList",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getReceiptList(@RequestBody Map<String, Object> map){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
                httpResponseEntity.setData(receiptService.getList(map));
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("updateTask 修改回执单信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


}
