package com.example.financialmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.example.financialmanagement.mapper")
public class FinancialManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinancialManagementApplication.class, args);
  }

}
