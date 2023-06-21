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

/**
 * <p>
 * 回执单 前端控制器
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptAction {


    @Autowired
    private ReceiptService receiptService;

    private final Logger logger = LoggerFactory.getLogger(TaskAction.class);

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Receipt> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(receiptService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Receipt params) {
        receiptService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        receiptService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Receipt params) {
        receiptService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
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


}
