package com.ankit.pojo.product;

import com.ankit.pojo.CommonResponsePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse extends CommonResponsePojo {

    private List<ProductResponsePOJO> productsList;
}
