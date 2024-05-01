package com.ankit.pojo.product;

import com.ankit.pojo.CommonResponsePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse extends CommonResponsePojo {

    private ProductResponsePOJO productDetail;
}
