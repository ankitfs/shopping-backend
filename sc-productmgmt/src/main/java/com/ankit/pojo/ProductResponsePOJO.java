package com.ankit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponsePOJO extends CommonResponsePojo{
    private int pid;
    private String pname;
    private String pdescription;
    private String pSKU;
    private ProductCategoryPOJO category;
    private int inventory;
    private BigDecimal price;
    private BigDecimal discount;
    private boolean active;
    private Timestamp creationDate;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ProductCategoryPOJO {
    private String categoryName;
    private String parentCategoryName;
}