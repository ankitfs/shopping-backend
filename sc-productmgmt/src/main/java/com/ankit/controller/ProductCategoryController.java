package com.ankit.controller;

import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.productcategory.ProductCategoryList;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/category")
public class ProductCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    //API for Getting All Top Level Categories
    @GetMapping("/all")
    public ProductCategoryList getAllParentCategories() {
        ProductCategoryList categoryList = new ProductCategoryList();
        try {
            categoryList.setCategoryList(productCategoryService.getAllCategories());
            categoryList.setStatus(true);
            categoryList.setReturnCode(200);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            categoryList.setStatus(false);
            categoryList.setReturnCode(500);
            categoryList.setMessage("Server Error");
        }
        return categoryList;
    }

    //API for creating a new category
    @PostMapping
    public CommonResponsePojo createProductCategory(@RequestBody ProductCategoryPOJO categoryCreatePojo){
        logger.info("Entered");
        CommonResponsePojo commonResponse = new CommonResponsePojo();
        try {
            logger.info("Category Bean : {}",categoryCreatePojo);
            commonResponse = productCategoryService.createCategory(categoryCreatePojo);
            commonResponse.setStatus(true);
            commonResponse.setReturnCode(201);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            commonResponse.setStatus(false);
            commonResponse.setReturnCode(500);
            commonResponse.setMessage("Server Error");
        }
        return commonResponse;
    }

    //API for updating a category
    @PutMapping
    public CommonResponsePojo updateCategoryHandler(@RequestBody ProductCategoryPOJO categoryPOJO) {
        CommonResponsePojo responsePojo = new CommonResponsePojo();
        try {
            responsePojo = productCategoryService.updateCategory(categoryPOJO);
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

    //API for deleting a category
    @DeleteMapping("/{categoryId}/{level}")
    public CommonResponsePojo deleteCategoryHandler(@PathVariable("categoryId") Integer categoryId, @PathVariable("level") Integer level) {
        CommonResponsePojo responsePojo = new CommonResponsePojo();
        try {
           responsePojo = productCategoryService.deleteCategory(categoryId, level);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            responsePojo.setStatus(false);
            responsePojo.setReturnCode(500);
            responsePojo.setMessage("Internal Server Error");
        }
        return responsePojo;
    }

    //API for getting subcategory
    @GetMapping("/{parentId}")
    public ProductCategoryList getSubCategories(@PathVariable("parentId") Integer parentId) {
        ProductCategoryList categoryList = new ProductCategoryList();
        try {
            categoryList.setCategoryList(productCategoryService.getSubCategories(parentId));
            categoryList.setStatus(true);
            categoryList.setReturnCode(200);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            categoryList.setStatus(false);
            categoryList.setReturnCode(500);
            categoryList.setMessage("Server Error");
        }
        return categoryList;
    }
}
