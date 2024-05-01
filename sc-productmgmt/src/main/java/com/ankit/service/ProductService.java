package com.ankit.service;

import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.product.ProductDetailResponse;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.pojo.productcategory.ProductCreateUpdatePojo;
import com.ankit.pojo.product.ProductListResponse;
import com.ankit.pojo.product.ProductResponsePOJO;

public interface ProductService {

    //Get the List of all the products
    public ProductListResponse getAllProducts() throws Exception;

    public ProductDetailResponse getProductDetail(String productSKU) throws Exception;

    public CommonResponsePojo createProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception;

    public void deleteProduct(String productSKU) throws Exception;

    public CommonResponsePojo updateProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception;

    public ProductListResponse getAllProductsByCategory(Integer categoryId) throws Exception;
}
