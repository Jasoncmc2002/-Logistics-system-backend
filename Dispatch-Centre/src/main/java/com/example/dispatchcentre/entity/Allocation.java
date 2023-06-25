package com.example.dispatchcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品调拨
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
@TableName("good_allocation")
public class Allocation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long taskId;

    /**
     * 用户名
     */
    private String outStation;

    /**
     * 用户名
     */
    private String inStation;

    /**
     * 用户名
     */
    private Date allocationDate;

    /**
     * 类型
     */
    private Byte alloType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getOutStation() {
        return outStation;
    }

    public void setOutStation(String outStation) {
        this.outStation = outStation;
    }

    public String getInStation() {
        return inStation;
    }

    public void setInStation(String inStation) {
        this.inStation = inStation;
    }

    public Date getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(Date allocationDate) {
        this.allocationDate = allocationDate;
    }

    public Byte getAlloType() {
        return alloType;
    }

    public void setAlloType(Byte alloType) {
        this.alloType = alloType;
    }

    @Override
    public String toString() {
        return "Allocation{" +
        ", id = " + id +
        ", taskId = " + taskId +
        ", outStation = " + outStation +
        ", inStation = " + inStation +
        ", allocationDate = " + allocationDate +
        ", alloType = " + alloType +
        "}";
    }
}
