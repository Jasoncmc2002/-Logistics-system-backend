package com.example.dispatchcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
public class Good implements Serializable {

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
    private String goodSubclass;

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
    private Double goodPrice;

    /**
     * 用户名
     */
    private Double goodSale;

    /**
     * 用户名
     */
    private Double goodCost;

    /**
     * 用户名
     */
    private String type;

    /**
     * 用户名
     */
    private String supply;

    /**
     * 用户名
     */
    private String goodFactory;

    /**
     * 用户名
     */
    private String sellDate;

    /**
     * 用户名
     */
    private Byte isReturn;

    /**
     * 用户名
     */
    private Byte isChange;

    /**
     * 用户名
     */
    private String remark;
    private Integer classId;
    private Integer keyId;

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

    public Double getGoodSale() {
        return goodSale;
    }

    public void setGoodSale(Double goodSale) {
        this.goodSale = goodSale;
    }

    public Double getGoodCost() {
        return goodCost;
    }

    public void setGoodCost(Double goodCost) {
        this.goodCost = goodCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getGoodFactory() {
        return goodFactory;
    }

    public void setGoodFactory(String goodFactory) {
        this.goodFactory = goodFactory;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public Byte getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Byte isReturn) {
        this.isReturn = isReturn;
    }

    public Byte getIsChange() {
        return isChange;
    }

    public void setIsChange(Byte isChange) {
        this.isChange = isChange;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    @Override
    public String toString() {
        return "Good{" +
        ", id = " + id +
        ", goodClass = " + goodClass +
        ", goodSubclass = " + goodSubclass +
        ", goodName = " + goodName +
        ", goodUnit = " + goodUnit +
        ", goodPrice = " + goodPrice +
        ", goodSale = " + goodSale +
        ", goodCost = " + goodCost +
        ", type = " + type +
        ", supply = " + supply +
        ", goodFactory = " + goodFactory +
        ", sellDate = " + sellDate +
        ", isReturn = " + isReturn +
        ", isChange = " + isChange +
        ", remark = " + remark +
        ", classId = " + classId +
        ", keyId = " + keyId +
        "}";
    }
}
