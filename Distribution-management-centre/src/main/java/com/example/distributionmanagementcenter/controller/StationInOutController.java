package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.StationInOut;
import com.example.distributionmanagementcenter.service.StationInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 库房出库 前端控制器
 * </p>
 *
 * @author Jason_Cai
 * @since 2023-06-20
 */
@Controller
@RequestMapping("/stationInOut")
public class StationInOutController {


    @Autowired
    private StationInOutService stationInOutService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<StationInOut>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<StationInOut> aPage = stationInOutService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StationInOut> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(stationInOutService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody StationInOut params) {
        stationInOutService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        stationInOutService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody StationInOut params) {
        stationInOutService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
