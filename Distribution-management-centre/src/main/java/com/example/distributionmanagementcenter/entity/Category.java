package com.example.distributionmanagementcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@TableName("class_category")
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value="f_name")
    private String fName;
    @TableField(value="s_name")
    private String sName;


}
