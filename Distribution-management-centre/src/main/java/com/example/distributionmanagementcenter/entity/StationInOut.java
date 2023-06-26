package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 库房出库
 * </p>
 *
 * @author Jason_Cai
 * @since 2023-06-20
 */
@TableName("in_station")
@Data
public class StationInOut implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Integer stationClass;

    /**
     * 用户名
     */
    private Long alloId;

    /**
     * 用户名
     */
    private Long stationId;

    /**
     * 用户名
     */
    private Long taskId;

    /**
     * 用户名
     */
    private Long goodId;

    /**
     * 用户名
     */
    private String goodClass;

    /**
     * 用户名
     */
    private Double goodPrice;

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
    private String goodFactory;

    /**
     * 用户名
     */
    private Integer number;

    /**
     * 用户名
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 用户名
     */
    private String remark;
    private Integer type;


}
