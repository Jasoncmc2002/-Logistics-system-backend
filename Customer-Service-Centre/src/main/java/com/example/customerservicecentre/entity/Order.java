package com.example.customerservicecentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String creater;

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
    private String goodNumber;

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
    private Double goodSum;

    /**
     * 用户名
     */
    private String explain;

    /**
     * 用户名
     */
    private String remark;

    /**
     * 用户名
     */
    private String substation;

    /**
     * 用户名
     */
    private Date orderDate;

    /**
     * 用户名
     */
    private Date deliveryDate;

    /**
     * 用户名
     */
    private String orderStype;

    /**
     * 用户名
     */
    private String ordersTatus;

    /**
     * 用户名
     */
    private String customerAddress;

    /**
     * 用户名
     */
    private String customerId;

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
    private String postcode;

    /**
     * 用户名
     */
    private Integer isInvoice;

    /**
     * 用户名
     */
    private String goodStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getGoodClass() {
        return goodClass;
    }

    public void setGoodClass(String goodClass) {
        this.goodClass = goodClass;
    }

    public String getGoodSubclass() {
        return goodSubclass;
    }

    public void setGoodSubclass(String goodSubclass) {
        this.goodSubclass = goodSubclass;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(String goodNumber) {
        this.goodNumber = goodNumber;
    }

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Double getGoodSum() {
        return goodSum;
    }

    public void setGoodSum(Double goodSum) {
        this.goodSum = goodSum;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderStype() {
        return orderStype;
    }

    public void setOrderStype(String orderStype) {
        this.orderStype = orderStype;
    }

    public String getOrdersTatus() {
        return ordersTatus;
    }

    public void setOrdersTatus(String ordersTatus) {
        this.ordersTatus = ordersTatus;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getGoodStatus() {
        return goodStatus;
    }

    public void setGoodStatus(String goodStatus) {
        this.goodStatus = goodStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
        ", id = " + id +
        ", creater = " + creater +
        ", goodClass = " + goodClass +
        ", goodSubclass = " + goodSubclass +
        ", goodName = " + goodName +
        ", goodNumber = " + goodNumber +
        ", goodUnit = " + goodUnit +
        ", goodPrice = " + goodPrice +
        ", goodSum = " + goodSum +
        ", explain = " + explain +
        ", remark = " + remark +
        ", substation = " + substation +
        ", orderDate = " + orderDate +
        ", deliveryDate = " + deliveryDate +
        ", orderStype = " + orderStype +
        ", ordersTatus = " + ordersTatus +
        ", customerAddress = " + customerAddress +
        ", customerId = " + customerId +
        ", customerName = " + customerName +
        ", mobilephone = " + mobilephone +
        ", postcode = " + postcode +
        ", isInvoice = " + isInvoice +
        ", goodStatus = " + goodStatus +
        "}";
    }
}
