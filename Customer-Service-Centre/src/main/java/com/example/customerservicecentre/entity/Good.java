package com.example.customerservicecentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Data
@TableName("good")
public class Good implements Serializable {

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
    private String goodSubclass;

    /**
     * 用户名
     */
    private String goodName;

    /**
     * 用户名
     */
    private String goodUnit;

    /**
     * 用户名
     */
    private Long goodNumber;
    /**
     * 用户名
     */
    private Double goodPrice;

    /**
     * 用户名
     */
    private Double goodSale;

    /**
     * 用户名
     */
    private Double goodCost;

    /**
     * 用户名
     */
    private String type;

    /**
     * 用户名
     */
    private String supply;

    /**
     * 用户名
     */
    private String goodFactory;

    /**
     * 用户名
     */
    private String sellDate;

    /**
     * 用户名
     */
    private Byte isReturn;

    /**
     * 用户名
     */
    private Byte isChange;

    /**
     * 用户名
     */
    private String remark;
    private Integer classId;
    private Integer keyId;

}
