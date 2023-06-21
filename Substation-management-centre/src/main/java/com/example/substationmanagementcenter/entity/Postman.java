package com.example.substationmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 配送员
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
public class Postman implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配送员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配送员姓名
     */
    private String postmanName;

    /**
     * 配送员电话
     */
    private String postmanPhonenum;

    /**
     * 配送员所属分站
     */
    private String postmanStation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostmanName() {
        return postmanName;
    }

    public void setPostmanName(String postmanName) {
        this.postmanName = postmanName;
    }

    public String getPostmanPhonenum() {
        return postmanPhonenum;
    }

    public void setPostmanPhonenum(String postmanPhonenum) {
        this.postmanPhonenum = postmanPhonenum;
    }

    public String getPostmanStation() {
        return postmanStation;
    }

    public void setPostmanStation(String postmanStation) {
        this.postmanStation = postmanStation;
    }

    @Override
    public String toString() {
        return "Postman{" +
        ", id = " + id +
        ", postmanName = " + postmanName +
        ", postmanPhonenum = " + postmanPhonenum +
        ", postmanStation = " + postmanStation +
        "}";
    }
}
