package com.ankit.pojo.productcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryPOJO {
    private Integer categoryId;
    private String categoryName;
    private String parentCategoryName;
    private Integer parentCategoryId;
    private Integer level;
    private Boolean active;

    public ProductCategoryPOJO(String categoryName, String parentCategoryName) {
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
    }
}
