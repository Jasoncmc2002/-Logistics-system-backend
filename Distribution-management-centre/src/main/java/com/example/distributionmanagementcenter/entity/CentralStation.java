package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 中心库房存量 
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@TableName("stock_central_station")
public class CentralStation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String goodClass;

    /**
     * 用户名
     */
    private String goodName;

    /**
     * 用户名
     */
    private Integer stock;

    /**
     * 用户名
     */
    private Integer withdrawal;

    /**
     * 用户名
     */
    private Integer waitAllo;

    /**
     * 用户名
     */
    private Integer doneAllo;

    /**
     * 用户名
     */
    private Integer warn;

    /**
     * 用户名
     */
    private Integer max;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodClass() {
        return goodClass;
    }

    public void setGoodClass(String goodClass) {
        this.goodClass = goodClass;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Integer withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Integer getWaitAllo() {
        return waitAllo;
    }

    public void setWaitAllo(Integer waitAllo) {
        this.waitAllo = waitAllo;
    }

    public Integer getDoneAllo() {
        return doneAllo;
    }

    public void setDoneAllo(Integer doneAllo) {
        this.doneAllo = doneAllo;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "CentralStation{" +
        ", id = " + id +
        ", goodClass = " + goodClass +
        ", goodName = " + goodName +
        ", stock = " + stock +
        ", withdrawal = " + withdrawal +
        ", waitAllo = " + waitAllo +
        ", doneAllo = " + doneAllo +
        ", warn = " + warn +
        ", max = " + max +
        "}";
    }
}
