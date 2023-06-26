package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 供应商
 * </p>
 *
 * @author jason_cai
 * @since 2023-06-19
 */
@Data
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户名
     */
    private String address;

    /**
     * 用户名
     */
    private String adminName;

    /**
     * 用户名
     */
    private String phone;

    /**
     * 用户名
     */
    private String bank;

    /**
     * 用户名
     */
    private String bankNumer;

    /**
     * 用户名
     */
    private String fax;

    /**
     * 用户名
     */
    private String postcode;

    /**
     * 用户名
     */
    private String legalPerson;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNumer() {
        return bankNumer;
    }

    public void setBankNumer(String bankNumer) {
        this.bankNumer = bankNumer;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Supply{" +
        ", id = " + id +
        ", name = " + name +
        ", address = " + address +
        ", adminName = " + adminName +
        ", phone = " + phone +
        ", bank = " + bank +
        ", bankNumer = " + bankNumer +
        ", fax = " + fax +
        ", postcode = " + postcode +
        ", legalPerson = " + legalPerson +
        ", remark = " + remark +
        "}";
    }
}
