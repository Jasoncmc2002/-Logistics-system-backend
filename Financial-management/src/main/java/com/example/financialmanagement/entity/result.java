package com.example.financialmanagement.entity;

import com.github.pagehelper.PageInfo;
import java.util.Date;
import lombok.Data;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-20 11:48
 */
@Data
public class result {
  private PageInfo pageInfo;
  private double sumMoney;
}
