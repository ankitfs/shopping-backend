package com.ankit.service.impl;

import com.ankit.dao.ProductCategoryRepository;
import com.ankit.entity.category.ProductCategoryEntity;
import com.ankit.exception.InvalidRequestException;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.utils.StringUtils;

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
        List<ProductCategoryEntity> categoryEntityList = productCategoryRepository.findParentCategories();
        logger.info("top level categories list from entity: {}", categoryEntityList);
        categoryEntityList.forEach(categoryEntity -> {
            ProductCategoryPOJO category = new ProductCategoryPOJO();
            category.setCategoryId(categoryEntity.getId());
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

        if(StringUtils.isEmpty(categoryPOJO.getCategoryName()) ||
        (categoryPOJO.getLevel() > 0 && categoryPOJO.getParentCategoryId() < 1))    //Checking if request category level is root
        {
            throw new InvalidRequestException("Invalid Request Body");
        }

        // Validating if category name not exists and parent category exists and parent category level is correct
        Long countCategory = productCategoryRepository.isExists(categoryPOJO.getCategoryName(), categoryPOJO.getParentCategoryId(), categoryPOJO.getLevel());
        if(countCategory != null && countCategory > 0){
            throw new InvalidRequestException("Requested Category Name already Exists");
        }

        ProductCategoryEntity categoryEntity = new ProductCategoryEntity();
        categoryEntity.setName(categoryPOJO.getCategoryName());

        if(categoryPOJO.getParentCategoryId() != null) {
            ProductCategoryEntity parentObj = new ProductCategoryEntity();
            parentObj.setId(categoryPOJO.getParentCategoryId());
            categoryEntity.setParentId(parentObj);
        }
        categoryEntity.setLevel(categoryPOJO.getLevel());
        categoryEntity.setActive(categoryPOJO.getActive());
        categoryEntity.setCreatedAt(Timestamp.from(Instant.now()));

        logger.debug("CategoryEntity :{}",categoryEntity);

        categoryEntity =  productCategoryRepository.save(categoryEntity);
        responsePojo.setMessage("Product Category "+categoryPOJO.getCategoryName()+" has been created ");
        return responsePojo;
    }

    @Override
    public CommonResponsePojo deleteCategory(Integer categoryId, Integer level) throws Exception {
        CommonResponsePojo commonResponsePojo = new CommonResponsePojo();
        //Checking whether category have any dependency
        List<ProductCategoryEntity> productCategoryList = productCategoryRepository.findByParentId(categoryId);

        if(productCategoryList != null && !productCategoryList.isEmpty()) {
            commonResponsePojo.setStatus(false);
            commonResponsePojo.setMessage("This Category is not empty. Kindly first delete child for this category");
        }
        else {
            productCategoryRepository.deleteByIdandLevel(categoryId, level);
            commonResponsePojo.setStatus(true);
            commonResponsePojo.setReturnCode(200);
            commonResponsePojo.setMessage("Category has been deleted");
        }

        return commonResponsePojo;
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

    @Override
    public List<ProductCategoryPOJO> getSubCategories(Integer parentCategoryId) throws Exception {
        List<ProductCategoryPOJO> categoriesPojoList = new ArrayList<>();
        ProductCategoryEntity parentEntity = new ProductCategoryEntity();
        parentEntity.setParentId(new ProductCategoryEntity(parentCategoryId));
        List<ProductCategoryEntity> categoryEntityList = productCategoryRepository.findByParentId(parentCategoryId);

        logger.info("categories list from entity: {}", categoryEntityList);
        categoryEntityList.forEach(categoryEntity -> {
            ProductCategoryPOJO category = new ProductCategoryPOJO();
            category.setCategoryId(categoryEntity.getId());
            category.setCategoryName(categoryEntity.getName());
            category.setParentCategoryName(categoryEntity.getParentId() != null ? categoryEntity.getParentId().getName() : null);
            category.setLevel(categoryEntity.getLevel());
            categoriesPojoList.add(category);
        });
        return categoriesPojoList;

    }
}
