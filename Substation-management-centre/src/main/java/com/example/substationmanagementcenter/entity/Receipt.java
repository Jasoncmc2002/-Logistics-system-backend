package com.example.substationmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 回执单
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Data
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Integer replyClass;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户名
     */
    private String mobilephone;

    /**
     * 用户名
     */
    private String substation;

    /**
     * 用户名
     */
    private String taskType;

    /**
     * 用户名
     */
    private String address;

    /**
     * 用户名
     */
    private String goodName;

    /**
     * 用户名
     */
    private String number;


    /**
     * 用户名
     */
    private Double price;

    /**
     * 用户名
     */
    private String taskStatus;

    /**
     * 用户名
     */
    private String customerSatis;

    /**
     * 用户名
     */
    private String remark;

    /**
     * 总额
     */
    private Long goodSum;

    private Long invoiceNumber;
    private String postman;
}
