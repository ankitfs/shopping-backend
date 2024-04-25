package com.ankit.service.impl;

import com.ankit.dao.ProductCategoryRepository;
import com.ankit.entity.ProductCategoryEntity;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryEntity getProductCategoryDetail(String categoryName, String parentCategoryName) throws Exception{
        ProductCategoryEntity productCategory = productCategoryRepository.findByChildAndParentCategoryName(categoryName, parentCategoryName);
        logger.info("product Category response::{}",productCategory);
        return productCategory;
    }

    @Override
    public List<ProductCategoryPOJO> getAllCategories() throws Exception{
        List<ProductCategoryPOJO> categoriesPojoList = new ArrayList<>();
        List<ProductCategoryEntity> categoryEntityList = productCategoryRepository.findAll();
        logger.info("categories list from entity: {}", categoryEntityList);
        categoryEntityList.forEach(categoryEntity -> {
            ProductCategoryPOJO category = new ProductCategoryPOJO();
            category.setCategoryName(categoryEntity.getName());
            category.setParentCategoryName(categoryEntity.getParentId() != null ? categoryEntity.getParentId().getName() : null);
            category.setLevel(categoryEntity.getLevel());
            categoriesPojoList.add(category);
        });
        return categoriesPojoList;
    }
}
