package com.ankit.controller;

import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.ProductCreateUpdatePojo;
import com.ankit.pojo.ProductListResponse;
import com.ankit.pojo.ProductResponsePOJO;
import com.ankit.service.ProductService;
import com.ankit.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            productListResponse.setMessage("Server Exception");
        }
        return productListResponse;
    }

    //Handle Product Creation Request
    @PostMapping
    public CommonResponsePojo createProduct(@RequestBody ProductCreateUpdatePojo request){
        System.out.println("Entered createProduct Controller");
        CommonResponsePojo response = null;
        try {
            response = productService.createProduct(request);
        }
        catch (Exception ex) {
            response = new CommonResponsePojo();

            response.setStatus(false);
            response.setReturnCode(500);
            response.setMessage("Internal Server Error");
        }

        return response;
    }
}
