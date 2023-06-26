package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 中心库房存量 
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@TableName("stock_central_station")
@Data
public class CentralStation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String goodClass;

    /**
     * 用户名
     */
    private String goodName;

    /**
     * 用户名
     */
    private Integer stock;

    /**
     * 用户名
     */
    private Integer withdrawal;

    /**
     * 用户名
     */
    private Integer waitAllo;

    /**
     * 用户名
     */
    private Integer doneAllo;

    /**
     * 用户名
     */
    private Integer warn;

    /**
     * 用户名
     */
    private Integer max;

    //2023.6.26添加字段
    @TableField("good_price")
    private double goodPrice;
    @TableField("good_sale")
    private double goodSale;
    @TableField("good_cost")
    private double goodCost;
    @TableField("good_unit")
    private String goodUnit;
    @TableField("supply_name")
    private String supplyName;



}
