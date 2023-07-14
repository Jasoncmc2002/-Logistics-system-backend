package com.example.substationmanagementcenter.sevice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.common.utils.DateUtil;
import com.example.substationmanagementcenter.entity.Good;
import com.example.substationmanagementcenter.entity.Receipt;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.entity.Use;
import com.example.substationmanagementcenter.feign.FeignApi;
import com.example.substationmanagementcenter.mapper.ReceiptMapper;
import com.example.substationmanagementcenter.mapper.TaskMapper;
import com.example.substationmanagementcenter.sevice.ReceiptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 回执单 服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Service
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private FeignApi feignApi;

    @Override
    public int updatebyId(Receipt receipt) {
        return receiptMapper.updateById(receipt);
    }

    @Override
    public int receiptEntry(Map<String, Object> map) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        int res = 0;

        Task task = taskMapper.selectById(String.valueOf(map.get("id")));

        Receipt receipt = new Receipt();
//        receipt.setGoodPrice(Double.parseDouble(String.valueOf(map.get("goodPrice"))));
        receipt.setTaskId(Long.valueOf(String.valueOf(map.get("id"))));//任务单号
        System.out.println("2");
        receipt.setCustomerName(String.valueOf(map.get("customerName")));//姓名
        receipt.setMobilephone(String.valueOf(map.get("customerPhone")));//联系电话
        receipt.setSubstation(String.valueOf(map.get("substation")));//任务分站
        System.out.println("3");
        receipt.setTaskType(String.valueOf(map.get("taskType")));//任务类型
        receipt.setAddress(String.valueOf(map.get("address")));//送/退货地址
//        receipt.setGoodName(String.valueOf(map.get("goodName")));//商品名称
        //        receipt.setNumber(String.valueOf(map.get("number")));//退货
        receipt.setTaskStatus(String.valueOf(map.get("taskStatus")));//任务状态
        receipt.setPrice(Double.parseDouble(String.valueOf(map.get("goodSum"))));//（退货）金额
        System.out.println("1");
        receipt.setCustomerSatis(String.valueOf(map.get("customerSatis")));
        receipt.setRemark(String.valueOf(map.get("remark")));
        receipt.setPostman(String.valueOf(map.get("postman")));

//        receipt.setGoodSum(Long.valueOf(String.valueOf(map.get("goodSum"))));

        Date  now= DateUtil.getCreateTime();
        receipt.setDate(now);



        int resReturn = 0;
        if(receipt.getTaskStatus() != null&&receipt.getTaskStatus().equals("退货")){
            QueryWrapper<Receipt> queryWrapper = new QueryWrapper<>();
            Long oldTaskId = feignApi.getOrTaskId(task.getId());
            queryWrapper.eq("task_id",oldTaskId);
            List<Receipt> receipts = receiptMapper.selectList(queryWrapper);
            Receipt receipt1 = receipts.get(receipts.size()-1);

            resReturn = feignApi.setPutAway(receipt1.getInvoiceNumber());
            Use use = new Use();
            use.setOrderId(task.getOrderId());
            use.setMoney(receipt.getPrice());
            use.setStation(receipt.getSubstation());
            use.setName(receipt.getPostman());
            HttpResponseEntity resUserNumber = feignApi.addReceiptInvoice(use);
            String jsonString1 = JSON.toJSONString(resUserNumber.getData());  // 将对象转换成json格式数据
            System.out.println("resUserNumber.getData()!!"+resUserNumber.getData());
//            JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
            Long userNumber = Long.valueOf(jsonString1);

            receipt.setInvoiceNumber(userNumber);

        }else if(receipt.getTaskStatus() != null&&receipt.getTaskStatus().equals("换货")){
            QueryWrapper<Receipt> queryWrapper = new QueryWrapper<>();
            Long oldTaskId = feignApi.getOrTaskId(task.getId());
            queryWrapper.eq("task_id",oldTaskId);
            List<Receipt> receipts = receiptMapper.selectList(queryWrapper);
            Receipt receipt1 = receipts.get(receipts.size()-1);

            receipt.setInvoiceNumber(receipt1.getInvoiceNumber());
        }else {
            Use use = new Use();
            use.setOrderId(task.getOrderId());
            use.setMoney(receipt.getPrice());
            use.setStation(receipt.getSubstation());
            use.setName(receipt.getPostman());
            HttpResponseEntity resUserNumber = feignApi.addReceiptInvoice(use);
            String jsonString1 = JSON.toJSONString(resUserNumber.getData());  // 将对象转换成json格式数据
            System.out.println("resUserNumber.getData()!!"+resUserNumber.getData());
//            JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
            Long userNumber = Long.valueOf(jsonString1);

            receipt.setInvoiceNumber(userNumber);
        }

        res = receiptMapper.insert(receipt);


        //改订单状态
        Map map1 =new HashMap<String,Object>();
        map1.put("id",task.getId());
        map1.put("task_status","已完成");
        map1.put("order_status","已完成");
        HttpResponseEntity res2= feignApi.changeTaskOrderType(map1);
        System.out.println("changeTaskOrderType(map1)"+res2.getData());

        return (int)res2.getData();
    }

    @Override
    public List<Receipt> getList() throws ParseException {
        List<Receipt> records= receiptMapper.selectList(null);
        return records;
    }

    @Override
    public PageInfo selectReceiptByCriteria(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        //筛选
        QueryWrapper<Receipt> queryWrapper = new QueryWrapper<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        if(map.get("startLine") != null && !map.get("startLine").equals("")){

            // 格式化中国时区时间为指定格式的字符串
            String date = LocalDateTime.parse(String.valueOf(map.get("startLine")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date startline = simpleDateFormat.parse(date);
            System.out.println("start"+startline);
            queryWrapper.ge("date",startline);
        }
        if(map.get("endLine") != null && !map.get("endLine").equals("")){
            String date = LocalDateTime.parse(String.valueOf(map.get("endLine")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date endline = simpleDateFormat.parse(date);
            System.out.println("end!!!"+endline);
            queryWrapper.lt("date",endline);
        }
        if(map.get("taskType") != null && !map.get("taskType").equals("")){
            System.out.println("taskType");
            queryWrapper.eq("task_type",map.get("taskType"));
        }
//        if(map.get("postman") != null && !map.get("postman").equals("")){
//            queryWrapper.eq("postman",map.get("postman"));
//        }
        List<Receipt> res= receiptMapper.selectList(queryWrapper);
        System.out.println("res!!1"+res);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }
}
