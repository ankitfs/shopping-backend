package com.ankit.service;

import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.productcategory.ProductCreateUpdatePojo;
import com.ankit.pojo.product.ProductListResponse;
import com.ankit.pojo.product.ProductResponsePOJO;

public interface ProductService {

    //Get the List of all the products
    public ProductListResponse getAllProducts() throws Exception;

    public ProductResponsePOJO getProductDetail(String productSKU) throws Exception;

    public CommonResponsePojo createProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception;

    public void deleteProduct(String productSKU) throws Exception;
}
