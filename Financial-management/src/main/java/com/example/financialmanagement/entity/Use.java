package com.example.financialmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 发票
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-28
 */
@TableName("invoice_use")
public class Use implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long invoiceId;

    /**
     * 用户名
     */
    private String type;

    /**
     * 用户名
     */
    private Long number;

    /**
     * 用户名
     */
    private Integer batch;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户名
     */
    private Date date;

    /**
     * 用户名
     */
    private Long orderId;

    /**
     * 用户名
     */
    private Object money;

    /**
     * 用户名
     */
    private String station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Object getMoney() {
        return money;
    }

    public void setMoney(Object money) {
        this.money = money;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Use{" +
        ", id = " + id +
        ", invoiceId = " + invoiceId +
        ", type = " + type +
        ", number = " + number +
        ", batch = " + batch +
        ", name = " + name +
        ", date = " + date +
        ", orderId = " + orderId +
        ", money = " + money +
        ", station = " + station +
        "}";
    }
}
