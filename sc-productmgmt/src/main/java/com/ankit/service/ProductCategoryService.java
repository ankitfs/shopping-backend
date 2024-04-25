package com.ankit.service;

import com.ankit.entity.ProductCategoryEntity;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;

import java.util.List;

public interface ProductCategoryService {

    public ProductCategoryEntity getProductCategoryDetail(String categoryName, String parentCategoryName) throws Exception;

    public List<ProductCategoryPOJO> getAllCategories() throws Exception;
}
