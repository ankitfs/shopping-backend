package com.ankit.controller;

import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.product.ProductDetailResponse;
import com.ankit.pojo.productcategory.ProductCreateUpdatePojo;
import com.ankit.pojo.product.ProductListResponse;
import com.ankit.pojo.product.ProductResponsePOJO;
import com.ankit.service.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productService;

    //API for Getting all products
    @GetMapping("/all")
    public ProductListResponse getProductsList(){
        logger.info("Entered");
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
        logger.info("Exit");
        return productListResponse;
    }

    //API for Getting Product Details
    @GetMapping("/{sku}")
    public ProductDetailResponse getProductDetail(String productSKU) {
        ProductDetailResponse productPojo = null;
        try {
            productPojo = productService.getProductDetail(productSKU);
            productPojo.setStatus(true);
            productPojo.setReturnCode(200);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            productPojo.setStatus(false);
            productPojo.setReturnCode(500);
            productPojo.setMessage("Server Error");
        }
        return productPojo;
    }

    //API for Creating a New Product
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponsePojo createProduct(@RequestPart("data") ProductCreateUpdatePojo request,
                                            @RequestPart("thumbnailImage") MultipartFile thumbnailImage,
                                            @RequestPart("modelImage") MultipartFile modelImage,
                                            @RequestPart("realImage") MultipartFile realImage){
        logger.info("Entered");
        CommonResponsePojo response = null;
        try {
            request.setThumbnailImage(thumbnailImage);
            request.setModelImage(modelImage);
            request.setRealImage(realImage);
            response = productService.createProduct(request);
        }
        catch (Exception ex) {
            response = new CommonResponsePojo();
            logger.error(ex.getMessage());
            response.setStatus(false);
            response.setReturnCode(500);
            response.setMessage("Internal Server Error");
        }
        logger.info("Exit");
        return response;
    }

    //API for Deleting an Existing Product by SKU
    @DeleteMapping(value = "/{sku}")
    public CommonResponsePojo deleteProduct(@PathVariable("sku") String productSKU) {
        logger.info("Entered");
        logger.info("Product having SKU {} to be deleted",productSKU);
        CommonResponsePojo response = new CommonResponsePojo();
        try {
            productService.deleteProduct(productSKU);
            response.setStatus(true);
            response.setReturnCode(200);
            response.setMessage("Product has been deleted having SKU :"+productSKU);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setStatus(false);
            response.setMessage("Getting Error while delete product having SKU :"+productSKU);
        }
        return response;
    }

    //API for Updating an Existing Product
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponsePojo updateProduct(@RequestPart("data") ProductCreateUpdatePojo productPojo,
                                            @RequestPart("thumbnailImage") MultipartFile thumbnailImage,
                                            @RequestPart("modelImage") MultipartFile modelImage,
                                            @RequestPart("realImage") MultipartFile realImage) throws Exception {
        CommonResponsePojo responsePojo = new CommonResponsePojo();
        try {
            productPojo.setThumbnailImage(thumbnailImage);
            productPojo.setModelImage(modelImage);
            productPojo.setRealImage(realImage);
            responsePojo = productService.updateProduct(productPojo);
            responsePojo.setStatus(true);
            responsePojo.setReturnCode(200);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            responsePojo.setStatus(false);
            responsePojo.setReturnCode(500);
            responsePojo.setMessage("Server Error");
        }
        return responsePojo;
    }

    //API for Getting All Products for a Category
    @GetMapping("/list/{categoryId}")
    public ProductListResponse getAllProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        ProductListResponse productListResponse = new ProductListResponse();;
        try {
            productListResponse = productService.getAllProductsByCategory(categoryId);
            productListResponse.setStatus(true);
            productListResponse.setReturnCode(200);
        }
        catch (Exception ex) {
            productListResponse.setStatus(false);
            productListResponse.setReturnCode(500);
            productListResponse.setMessage("Server Error");
        }
        return productListResponse;
    }


}
