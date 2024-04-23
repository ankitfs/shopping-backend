package com.ankit.service.impl;

import com.ankit.dao.ProductCategoryRepository;
import com.ankit.dao.ProductRepository;
import com.ankit.entity.ProductCategoryEntity;
import com.ankit.entity.ProductEntity;
import com.ankit.entity.ProductInventoryEntity;
import com.ankit.exception.InvalidRequestException;
import com.ankit.pojo.*;
import com.ankit.service.ProductService;
import com.ankit.utility.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductListResponse getAllProducts() throws Exception {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductResponsePOJO> productsList = new ArrayList<>();
        List<ProductEntity> productEntity = productRepository.findAll();
            productEntity.forEach(p -> {
                System.out.println(p.toString());
            });
            //TODO:: parsing the response from dao layer into the pojo format
        productEntity.forEach(productEntity1 -> {
            ProductResponsePOJO product = new ProductResponsePOJO();
            product.setPid(productEntity1.getId());
            product.setPname(productEntity1.getName());
            product.setPdescription(productEntity1.getDescription());
            product.setPSKU(productEntity1.getSKU());
            product.setCategory(new ProductCategoryPOJO(
                    productEntity1.getCategoryId().getName(),
                    productEntity1.getCategoryId().getParentId().getName()));
            product.setPrice(productEntity1.getPrice());
            product.setActive(productEntity1.getActive());
            product.setInventory(productEntity1.getInventoryId().getQuantity());
            product.setCreationDate(productEntity1.getCreatedAt());
            productsList.add(product);
        });

        productListResponse.setProductsList(productsList);
        productListResponse.setStatus(true);
        productListResponse.setReturnCode(200);

        return productListResponse;
    }

    @Override
    public CommonResponsePojo createProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception {

        //Validation of Input Parameters like Not Null, Not Empty
        if(createUpdatePojo.getName() == null || createUpdatePojo.getName().trim().isEmpty() ||
           createUpdatePojo.getCategory().getCategoryName() == null || createUpdatePojo.getCategory().getCategoryName().isEmpty() ||
           createUpdatePojo.getCategory().getParentCategoryName() == null || createUpdatePojo.getCategory().getParentCategoryName().isEmpty() ||
           createUpdatePojo.getPrice() == null || createUpdatePojo.getPrice().equals(BigDecimal.ZERO) ||
           createUpdatePojo.getInventory() == null || createUpdatePojo.getInventory() <= 0 ||
           createUpdatePojo.getActive() == null
        ) {
            throw new InvalidRequestException("Invalid Request");
        }

        //Validation of Whether Category in Request body are valid
        ProductCategoryEntity productCategoryEntity = getProductCategoryDetail(createUpdatePojo.getCategory().getCategoryName(), createUpdatePojo.getCategory().getParentCategoryName());
        if(productCategoryEntity == null) {
            throw new InvalidRequestException("Input Categories didn't Exist");
        }

        //Product SKU generation utility
        String pSKU = HelperMethods.productSKUGenerator(createUpdatePojo.getName(), createUpdatePojo.getPrice());

        //Populating the Product Entity
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createUpdatePojo.getName());
        productEntity.setDescription(createUpdatePojo.getDescription());
        productEntity.setSKU(pSKU);
        productEntity.setCategoryId(productCategoryEntity);
        productEntity.setActive(createUpdatePojo.getActive());
        productEntity.setPrice(createUpdatePojo.getPrice());

        ProductInventoryEntity productInventoryEntity = new ProductInventoryEntity(createUpdatePojo.getInventory());
        productEntity.setInventoryId(productInventoryEntity);

        productEntity = productRepository.save(productEntity);
        System.out.println("Product has been created with ID:"+productEntity.getId());

        CommonResponsePojo commonResponsePojo = new CommonResponsePojo();

        if(productEntity.getId() != null) {
            commonResponsePojo.setStatus(true);
            commonResponsePojo.setReturnCode(201);
            commonResponsePojo.setMessage("Product has been created with ID:"+productEntity.getId());
        }

        return commonResponsePojo;
    }

    @Override
    public ProductCategoryEntity getProductCategoryDetail(String categoryName, String parentCategoryName) throws Exception{
        ProductCategoryEntity productCategory = productCategoryRepository.findByChildAndParentCategoryName(categoryName, parentCategoryName);
        System.out.println(productCategory);

        return productCategory;
    }
}
