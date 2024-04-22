package com.ankit.service;

import com.ankit.pojo.ProductListResponse;

public interface ProductService {

    //Get the List of all the products
    public ProductListResponse getAllProducts() throws Exception;
}
