package com.example.dispatchcentre.controller;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-07-07 11:24
 */
public class test  {
    public static void main(String[] args) {

      String utcDateString = "2023-11-10T02:10:00.000Z";

      // 将 LocalDateTime 对象转换为中国时区的 ZonedDateTime 对象
      ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
      // 格式化中国时区时间为指定格式的字符串
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String chinaDateString = LocalDateTime.parse(utcDateString, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
      System.out.println(chinaDateString);

    }
}
