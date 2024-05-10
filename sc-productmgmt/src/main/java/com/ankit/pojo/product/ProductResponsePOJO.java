package com.ankit.pojo.product;

import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponsePOJO {
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

    private String thumbnailImagePath;
    private String modelImagePath;
    private String realImagePath;
}

