package com.ankit.service.impl;

import com.ankit.dao.ProductCategoryRepository;
import com.ankit.entity.ProductCategoryEntity;
import com.ankit.exception.InvalidRequestException;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
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

    @Override
    public CommonResponsePojo createCategory(ProductCategoryPOJO categoryPOJO) throws Exception {
        CommonResponsePojo responsePojo = new CommonResponsePojo();

        if(categoryPOJO.getCategoryName() == null || categoryPOJO.getCategoryName().isEmpty() ||
           categoryPOJO.getParentCategoryId() == null || categoryPOJO.getParentCategoryId() > 0 ||
           categoryPOJO.getLevel() == null || categoryPOJO.getLevel() < 0) {
            throw new InvalidRequestException("Invalid Request Body");
        }

        // Validating if category name not exists and parent category exists and parent category level is correct
        Boolean countCategory = productCategoryRepository.isExists(categoryPOJO.getCategoryName(), categoryPOJO.getParentCategoryId(), categoryPOJO.getLevel());
        if(countCategory){
            throw new InvalidRequestException("Requested Category Name already Exists");
        }

        ProductCategoryEntity categoryEntity = new ProductCategoryEntity();
        categoryEntity.setName(categoryPOJO.getCategoryName());
        categoryEntity.setParentId(new ProductCategoryEntity(categoryPOJO.getParentCategoryId()));
        categoryEntity.setLevel(categoryPOJO.getLevel());
        categoryEntity.setCreatedAt(Timestamp.from(Instant.now()));

        categoryEntity =  productCategoryRepository.save(categoryEntity);
        responsePojo.setMessage("Product Category "+categoryPOJO.getCategoryName()+" has been created ");
        return responsePojo;
    }

    @Override
    public void deleteCategory(Integer categoryId, Integer level) throws Exception {
        productCategoryRepository.deleteByIdandLevel(categoryId, level);
    }

    @Override
    public CommonResponsePojo updateCategory(ProductCategoryPOJO categoryPOJO) throws Exception {
        CommonResponsePojo responsePojo = new CommonResponsePojo();

        ProductCategoryEntity categoryEntity = new ProductCategoryEntity();
        categoryEntity.setName(categoryPOJO.getCategoryName());
        categoryEntity.setParentId(new ProductCategoryEntity(categoryPOJO.getParentCategoryId()));
        categoryEntity.setLevel(categoryPOJO.getLevel());
        categoryEntity.setModifiedAt(Timestamp.from(Instant.now()));
        categoryEntity.setActive(categoryPOJO.getActive());

        categoryEntity = productCategoryRepository.save(categoryEntity);

        responsePojo.setMessage("Category :" + categoryEntity.getName() + "\t has been updated");

        return responsePojo;
    }


}
