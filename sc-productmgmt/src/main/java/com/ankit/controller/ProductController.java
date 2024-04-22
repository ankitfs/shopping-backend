package com.ankit.controller;

import com.ankit.pojo.ProductListResponse;
import com.ankit.pojo.ProductResponsePOJO;
import com.ankit.service.ProductService;
import com.ankit.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/all")
    public ProductListResponse getProducts(){
        ProductListResponse productListResponse = new ProductListResponse();
        ProductResponsePOJO productPojo = new ProductResponsePOJO();
        try {
            productListResponse = productService.getAllProducts();
        }
        catch (Exception ex) {
            productListResponse.setStatus(false);
            productListResponse.setReturnCode(500);
            productListResponse.setErrorMessage("Server Exception");
        }
        return productListResponse;
    }
}
