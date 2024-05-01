package com.ankit.pojo.productcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
//POJO Class used for creation and updation of a product item
public class ProductCreateUpdatePojo {

    private String name;
    private String description;
    private ProductCategoryPOJO category;
    private Integer inventory;
    private BigDecimal price;
    private Boolean active;
    private String SKU;
    private Integer level;

}
