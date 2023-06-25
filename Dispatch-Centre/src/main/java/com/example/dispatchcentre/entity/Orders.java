package com.example.dispatchcentre.entity;

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
 * @since 2023-06-20
 */
public class Orders implements Serializable {

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
    private String orderType;

    /**
     * 用户名
     */
    private String orderStatus;

    /**
     * 用户名
     */
    private String customerAddress;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户名
     */
    private String receiveName;

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
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
        return "Orders{" +
        ", id = " + id +
        ", creater = " + creater +
        ", goodSum = " + goodSum +
        ", explain = " + explain +
        ", remark = " + remark +
        ", substation = " + substation +
        ", orderDate = " + orderDate +
        ", deliveryDate = " + deliveryDate +
        ", orderType = " + orderType +
        ", orderStatus = " + orderStatus +
        ", customerAddress = " + customerAddress +
        ", customerName = " + customerName +
        ", receiveName = " + receiveName +
        ", mobilephone = " + mobilephone +
        ", postcode = " + postcode +
        ", isInvoice = " + isInvoice +
        ", goodStatus = " + goodStatus +
        "}";
    }
}
