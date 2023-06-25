package com.example.warehousemanagementcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 库房
 * </p>
 *
 * @author hzn
 * @since 2023-06-24
 */
public class Station implements Serializable {

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
    private String admin;

    /**
     * 用户名
     */
    private String grade;

    /**
     * 用户名
     */
    private Integer stationClass;

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

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getStationClass() {
        return stationClass;
    }

    public void setStationClass(Integer stationClass) {
        this.stationClass = stationClass;
    }

    @Override
    public String toString() {
        return "Station{" +
                ", id = " + id +
                ", name = " + name +
                ", address = " + address +
                ", admin = " + admin +
                ", grade = " + grade +
                ", stationClass = " + stationClass +
                "}";
    }
}
