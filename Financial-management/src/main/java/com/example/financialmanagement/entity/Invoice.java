package com.example.financialmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 发票
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Integer invoiceClass;

    /**
     * 用户名
     */
    private Long startNumber;

    /**
     * 用户名
     */
    private Long endNumber;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceClass() {
        return invoiceClass;
    }

    public void setInvoiceClass(Integer invoiceClass) {
        this.invoiceClass = invoiceClass;
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Long startNumber) {
        this.startNumber = startNumber;
    }

    public Long getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Long endNumber) {
        this.endNumber = endNumber;
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

    @Override
    public String toString() {
        return "Invoice{" +
        ", id = " + id +
        ", invoiceClass = " + invoiceClass +
        ", startNumber = " + startNumber +
        ", endNumber = " + endNumber +
        ", batch = " + batch +
        ", name = " + name +
        ", date = " + date +
        "}";
    }
}
