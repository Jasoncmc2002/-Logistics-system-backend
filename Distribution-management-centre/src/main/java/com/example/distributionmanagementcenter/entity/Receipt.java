package com.example.distributionmanagementcenter.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


@Data
@ColumnWidth(20)
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "回复类型")
    private Integer replyClass;
    @ExcelProperty(value = "用户名")
    private String customerName;
    @ExcelProperty(value = "电话号码")
    private String mobilephone;
    @ExcelProperty(value = "分站名")
    private String substation;
    @ExcelProperty(value = "任务类型")
    private String taskType;
    @ExcelProperty(value = "地址")
    private String address;
    @ExcelProperty(value = "商品名称")
    private String goodName;
    @ExcelProperty(value = "购买数量")
    private String number;
    @ExcelProperty(value = "商品价格")
    private Double price;
    @ExcelProperty(value = "订单状态")
    private String taskStatus;
    @ExcelProperty(value = "客户满意度")
    private String customerSatis;
    @ExcelProperty(value = "备注")
    private String remark;
    @ExcelProperty(value = "商品数量")
    private Long goodSum;
    @ExcelProperty(value = "发票单号")
    private Long invoiceNumber;
    @ExcelProperty(value = "投递员")
    private String postman;
}
