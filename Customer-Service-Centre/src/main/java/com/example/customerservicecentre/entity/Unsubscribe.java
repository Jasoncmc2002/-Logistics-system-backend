package com.example.customerservicecentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 退订
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@Data
public class Unsubscribe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private Long customerId;
    /**
     * 用户名
     */
    private Long orderId;
    /**
     * 用户名
     */
    private String creater;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户名
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderDate;

    /**
     * 用户名
     */
    private Long sum;

    /**
     * 用户名
     */
    private String ordersTatus;

    /**
     * 用户名
     */
    private String reason;

    /**
     * 用户名
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

}
