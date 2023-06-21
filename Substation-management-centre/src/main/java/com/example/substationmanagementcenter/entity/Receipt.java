package com.example.substationmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 回执单
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
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
    private Double goodPrice;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReplyClass() {
        return replyClass;
    }

    public void setReplyClass(Integer replyClass) {
        this.replyClass = replyClass;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCustomerSatis() {
        return customerSatis;
    }

    public void setCustomerSatis(String customerSatis) {
        this.customerSatis = customerSatis;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Receipt{" +
        ", id = " + id +
        ", replyClass = " + replyClass +
        ", customerName = " + customerName +
        ", mobilephone = " + mobilephone +
        ", substation = " + substation +
        ", taskType = " + taskType +
        ", address = " + address +
        ", goodName = " + goodName +
        ", number = " + number +
        ", goodPrice = " + goodPrice +
        ", price = " + price +
        ", taskStatus = " + taskStatus +
        ", customerSatis = " + customerSatis +
        ", remark = " + remark +
        "}";
    }
}
