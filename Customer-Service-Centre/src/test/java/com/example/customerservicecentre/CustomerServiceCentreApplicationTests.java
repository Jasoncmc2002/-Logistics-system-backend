package com.example.customerservicecentre;

import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceCentreApplicationTests {
  @Autowired
  private OrderService orderService;
  @Test
  void contextLoads() {

  }

}
