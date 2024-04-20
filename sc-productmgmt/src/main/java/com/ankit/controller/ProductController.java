package com.ankit.controller;

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
    public ProductResponsePOJO getProducts(){
        ProductResponsePOJO productPojo = new ProductResponsePOJO();
        try {
            productPojo = productService.getAllProducts();
        }
        catch (Exception ex) {
            productPojo.setStatus("error");
            productPojo.setReturnCode(500);
            productPojo.setErrorMessage(ex.getMessage());
        }
        return productPojo;
    }
}
