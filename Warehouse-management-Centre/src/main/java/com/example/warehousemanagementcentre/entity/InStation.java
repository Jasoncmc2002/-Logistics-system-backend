package com.example.warehousemanagementcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 库房出库
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
public class InStation implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    /**
     * 用户名
     */
    private String remark;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStationClass() {
        return stationClass;
    }

    public void setStationClass(Integer stationClass) {
        this.stationClass = stationClass;
    }

    public Long getAlloId() {
        return alloId;
    }

    public void setAlloId(Long alloId) {
        this.alloId = alloId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodClass() {
        return goodClass;
    }

    public void setGoodClass(String goodClass) {
        this.goodClass = goodClass;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }

    public String getGoodFactory() {
        return goodFactory;
    }

    public void setGoodFactory(String goodFactory) {
        this.goodFactory = goodFactory;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Station{" +
        ", id = " + id +
        ", stationClass = " + stationClass +
        ", alloId = " + alloId +
        ", stationId = " + stationId +
        ", taskId = " + taskId +
        ", goodId = " + goodId +
        ", goodClass = " + goodClass +
        ", goodPrice = " + goodPrice +
        ", goodName = " + goodName +
        ", goodUnit = " + goodUnit +
        ", goodFactory = " + goodFactory +
        ", number = " + number +
        ", date = " + date +
        ", remark = " + remark +
        ", type = " + type +
        "}";
    }
}
