package com.ankit.service.impl;

import com.ankit.dao.ProductRepository;
import com.ankit.entity.ProductEntity;
import com.ankit.pojo.ProductResponsePOJO;
import com.ankit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponsePOJO getAllProducts() throws Exception {
        ProductResponsePOJO productPojo = new ProductResponsePOJO();
        List<ProductEntity> productEntity = productRepository.findAll();
            productEntity.forEach(p -> {
                System.out.println(p.toString());
            });
            //TODO:: parsing the response from dao layer into the pojo format

        return productPojo;
    }
}
