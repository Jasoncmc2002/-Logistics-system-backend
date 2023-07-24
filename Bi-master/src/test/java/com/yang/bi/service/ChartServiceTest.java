package com.yang.bi.service;

import com.yang.bi.mapper.ChartMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author YANG FUCHAO
 * CreateTime 2023/5/27 17:13
 */
@SpringBootTest
class ChartServiceTest {

    @Resource
    private ChartMapper chartMapper;

    @Test
    void queryChartData() {
        String chartId = "1659210482555121666";
        String querySql = String.format("select * from chart_%s", chartId);
        List<Map<String, Object>> resultData = chartMapper.queryChartData(querySql);
        System.out.println(resultData);
    }
}
