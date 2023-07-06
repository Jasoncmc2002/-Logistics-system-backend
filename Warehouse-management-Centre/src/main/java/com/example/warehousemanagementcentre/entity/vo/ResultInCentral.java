package com.example.warehousemanagementcentre.entity.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzn
 * @create 2023-07-03 16:59
 */
@Data
public class ResultInCentral {
//    private PageInfo pageInfo;
    //供应商
    private int supply;
    //购货日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buyDate;
//    //签收人
//    private String signee;
//    //分发人
//    private String distributors;
    //签收日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiptDate;

    private String remark;

    private String goodName;

    private int goodClassId;

    private int goodSubclassId;

    private String goodUnit;

    private int buyNumber;



}
