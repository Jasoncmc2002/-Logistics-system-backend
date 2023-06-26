package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author jason_cai
 * @since 2023-06-19
 */
@Data
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
    private Integer classId;
    private Integer keyId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date goodDate;
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
    private Long goodNumber;
    /**
     * 用户名
     */
    private String goodUnit;

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

    /**
     * 用户名
     */

    public Long getId() {
        return id;
    }


}
