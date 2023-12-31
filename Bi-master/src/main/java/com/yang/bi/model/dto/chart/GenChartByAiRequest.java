package com.yang.bi.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @author YANG FUCHAO
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 图表名称
     */
    private String chartName;

    /**
     * 图表名称
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;
    //创建人
    private String creator;

    private static final long serialVersionUID = 1L;
}
