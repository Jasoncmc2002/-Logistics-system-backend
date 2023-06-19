package com.example.substationmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 退订
 * </p>
 *
 * @author hzn
 * @since 2023-06-19
 */
public class Task implements Serializable {

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
    private Long customerId;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户名
     */
    private Date taskDate;

    /**
     * 用户名
     */
    private Long goodId;

    /**
     * 用户名
     */
    private String goodName;

    /**
     * 用户名
     */
    private Integer goodNumber;

    /**
     * 用户名
     */
    private Date deadline;

    /**
     * 用户名
     */
    private String taskType;

    /**
     * 用户名
     */
    private String taskStatus;

    /**
     * 用户名
     */
    private String address;

    /**
     * 用户名
     */
    private String postman;

    /**
     * 用户名
     */
    private String substation;

    /**
     * 用户名
     */
    private Integer printNumber;

    /**
     * 用户名
     */
    private String creater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(Integer goodNumber) {
        this.goodNumber = goodNumber;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostman() {
        return postman;
    }

    public void setPostman(String postman) {
        this.postman = postman;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public Integer getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(Integer printNumber) {
        this.printNumber = printNumber;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String toString() {
        return "Task{" +
        ", id = " + id +
        ", orderId = " + orderId +
        ", customerId = " + customerId +
        ", customerName = " + customerName +
        ", taskDate = " + taskDate +
        ", goodId = " + goodId +
        ", goodName = " + goodName +
        ", goodNumber = " + goodNumber +
        ", deadline = " + deadline +
        ", taskType = " + taskType +
        ", taskStatus = " + taskStatus +
        ", address = " + address +
        ", postman = " + postman +
        ", substation = " + substation +
        ", printNumber = " + printNumber +
        ", creater = " + creater +
        "}";
    }
}
