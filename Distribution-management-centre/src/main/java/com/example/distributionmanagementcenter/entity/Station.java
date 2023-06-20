package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 库房
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@Data
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户名
     */
    private String address;

    /**
     * 用户名
     */
    private String admin;

    /**
     * 用户名
     */
    private String grade;

    /**
     * 用户名
     */
    private Integer stationClass;

    @TableField(exist = false)
    private List<CentralStation> centralStationList;


}
