package com.ankit.service;

import com.ankit.entity.category.ProductCategoryEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;

import java.util.List;

public interface ProductCategoryService {

    public ProductCategoryEntity getProductCategoryDetail(String categoryName, String parentCategoryName) throws Exception;

    public List<ProductCategoryPOJO> getAllCategories() throws Exception;

    public CommonResponsePojo createCategory(ProductCategoryPOJO categoryPOJO) throws Exception;

    public CommonResponsePojo deleteCategory(Integer categoryId, Integer level) throws Exception;

    public CommonResponsePojo updateCategory(ProductCategoryPOJO categoryPOJO) throws Exception;

    public List<ProductCategoryPOJO> getSubCategories(Integer parentCategoryId) throws Exception;
}
