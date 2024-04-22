package com.ankit.service.impl;

import com.ankit.dao.ProductRepository;
import com.ankit.entity.ProductCategoryEntity;
import com.ankit.entity.ProductEntity;
import com.ankit.pojo.ProductCategoryPOJO;
import com.ankit.pojo.ProductListResponse;
import com.ankit.pojo.ProductResponsePOJO;
import com.ankit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

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
}
