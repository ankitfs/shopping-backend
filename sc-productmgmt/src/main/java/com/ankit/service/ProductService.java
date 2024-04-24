package com.ankit.service;

import com.ankit.entity.ProductCategoryEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.ProductCreateUpdatePojo;
import com.ankit.pojo.ProductListResponse;

public interface ProductService {

    //Get the List of all the products
    public ProductListResponse getAllProducts() throws Exception;

    public CommonResponsePojo createProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception;

    public ProductCategoryEntity getProductCategoryDetail(String categoryName, String parentCategoryName) throws Exception;

    public void deleteProduct(String productSKU) throws Exception;
}
