package com.example.financialmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 退订
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@TableName("change_return")
public class Return implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long customerId;

    /**
     * 用户名
     */
    private String creater;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户名
     */
    private Date orderDate;

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
    private Double goodPrice;

    /**
     * 用户名
     */
    private Long sum;

    /**
     * 用户名
     */
    private String ordersTatus;

    /**
     * 用户名
     */
    private String reason;

    /**
     * 用户名
     */
    private Date date;

    /**
     * 用户名
     */
    private Integer orderClass;

    /**
     * 用户名
     */
    private Long originalOrderNumber;

    /**
     * 用户名
     */
    private Integer goodNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getOrdersTatus() {
        return ordersTatus;
    }

    public void setOrdersTatus(String ordersTatus) {
        this.ordersTatus = ordersTatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOrderClass() {
        return orderClass;
    }

    public void setOrderClass(Integer orderClass) {
        this.orderClass = orderClass;
    }

    public Long getOriginalOrderNumber() {
        return originalOrderNumber;
    }

    public void setOriginalOrderNumber(Long originalOrderNumber) {
        this.originalOrderNumber = originalOrderNumber;
    }

    public Integer getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(Integer goodNumber) {
        this.goodNumber = goodNumber;
    }

    @Override
    public String toString() {
        return "Return{" +
        ", id = " + id +
        ", customerId = " + customerId +
        ", creater = " + creater +
        ", customerName = " + customerName +
        ", orderDate = " + orderDate +
        ", goodId = " + goodId +
        ", goodName = " + goodName +
        ", goodPrice = " + goodPrice +
        ", sum = " + sum +
        ", ordersTatus = " + ordersTatus +
        ", reason = " + reason +
        ", date = " + date +
        ", orderClass = " + orderClass +
        ", originalOrderNumber = " + originalOrderNumber +
        ", goodNumber = " + goodNumber +
        "}";
    }
}
