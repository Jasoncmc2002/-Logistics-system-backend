package com.example.warehousemanagementcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品调拨
 * </p>
 *
 * @author hzn
 * @since 2023-06-25
 */
@Data
@TableName("good_allocation")
public class Allocation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long orderId;

    /**
     * 用户名
     */
    private String outStation;

    /**
     * 用户名
     */
    private String inStation;

    /**
     * 用户名
     */
    private Date allocationDate;

    /**
     * 类型
     */
    private Byte alloType;

    private String distributors;

    private String signer;


}
