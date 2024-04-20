package com.ankit.service;

import com.ankit.pojo.ProductResponsePOJO;

public interface ProductService {

    //Get the List of all the products
    public ProductResponsePOJO getAllProducts() throws Exception;
}
