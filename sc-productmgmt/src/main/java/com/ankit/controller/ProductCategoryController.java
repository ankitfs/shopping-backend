package com.ankit.controller;

import com.ankit.pojo.productcategory.ProductCategoryList;
import com.ankit.service.ProductCategoryService;
import com.ankit.utility.HelperMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productcategory")
public class ProductCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    //Get All Categories
    @GetMapping("/all")
    public ProductCategoryList getAlCategories() {
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

    //TODO:: Create Category

    //TODO:: Update Single Category
    
    //TODO:: Delete Category
}
