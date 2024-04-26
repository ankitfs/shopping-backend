package com.ankit.pojo.productcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryPOJO {
    private String categoryName;
    private String parentCategoryName;
    private Integer parentCategoryId;
    private Integer level;

    public ProductCategoryPOJO(String categoryName, String parentCategoryName) {
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
    }
}
