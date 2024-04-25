package com.ankit.pojo.productcategory;

import com.ankit.pojo.CommonResponsePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryList extends CommonResponsePojo {

    private List<ProductCategoryPOJO> categoryList;

}
