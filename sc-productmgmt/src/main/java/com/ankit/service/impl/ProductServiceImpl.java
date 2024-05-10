package com.ankit.service.impl;

import com.ankit.dao.ProductCategoryRepository;
import com.ankit.dao.ProductRepository;
import com.ankit.entity.ProductCategoryEntity;
import com.ankit.entity.ProductEntity;
import com.ankit.entity.ProductInventoryEntity;
import com.ankit.exception.InvalidRequestException;
import com.ankit.pojo.*;
import com.ankit.pojo.product.ProductDetailResponse;
import com.ankit.pojo.product.ProductListResponse;
import com.ankit.pojo.product.ProductResponsePOJO;
import com.ankit.pojo.productcategory.ProductCategoryPOJO;
import com.ankit.pojo.productcategory.ProductCreateUpdatePojo;
import com.ankit.service.ProductCategoryService;
import com.ankit.service.ProductService;
import com.ankit.utility.HelperMethods;
import com.ankit.utility.S3ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private S3ClientFactory s3ClientFactory;

    private final String s3BucketName = "my-developer-bucket-v1";
    private String s3ImagePath = null;

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

        // List buckets
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3ClientFactory.getS3Client().listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));

        return productListResponse;
    }

    @Override
    public ProductDetailResponse getProductDetail(String productSKU) throws Exception {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        ProductResponsePOJO productPojo = new ProductResponsePOJO();
        ProductEntity productEntity1 = productRepository.findBySKU(productSKU);
        logger.info("Product Entity Details::{}",productEntity1);
        productPojo.setPid(productEntity1.getId());
        productPojo.setPname(productEntity1.getName());
        productPojo.setPdescription(productEntity1.getDescription());
        productPojo.setPSKU(productEntity1.getSKU());
        productPojo.setCategory(new ProductCategoryPOJO(
                productEntity1.getCategoryId().getName(),
                productEntity1.getCategoryId().getParentId().getName()));
        productPojo.setPrice(productEntity1.getPrice());
        productPojo.setActive(productEntity1.getActive());
        productPojo.setInventory(productEntity1.getInventoryId().getQuantity());
        productPojo.setCreationDate(productEntity1.getCreatedAt());
        productDetailResponse.setProductDetail(productPojo);
        return productDetailResponse;
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
        ProductCategoryEntity productCategoryEntity = productCategoryService.getProductCategoryDetail(createUpdatePojo.getCategory().getCategoryName(), createUpdatePojo.getCategory().getParentCategoryName());
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
        productInventoryEntity.setCreatedAt(Timestamp.from(Instant.now()));

        productEntity.setInventoryId(productInventoryEntity);

        if(createUpdatePojo.getThumbnailImage() != null && !createUpdatePojo.getThumbnailImage().isEmpty()) {
            s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getThumbnailImage());

            productEntity.setThumbnailImagePath(s3ImagePath);
        }

        if (createUpdatePojo.getModelImage() != null && !createUpdatePojo.getModelImage().isEmpty()) {
            s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getModelImage());

            productEntity.setModelImagePath(s3ImagePath);
        }

        if(createUpdatePojo.getRealImage() != null && !createUpdatePojo.getRealImage().isEmpty()) {
            s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getRealImage());

            productEntity.setRealImagePath(s3ImagePath);
        }

        productEntity = productRepository.save(productEntity);
        logger.info("Product has been created with ID:"+productEntity.getId());

        CommonResponsePojo commonResponsePojo = new CommonResponsePojo();

        if(productEntity.getId() != null) {
            commonResponsePojo.setStatus(true);
            commonResponsePojo.setReturnCode(201);
            commonResponsePojo.setMessage("Product has been created with ID:"+productEntity.getId());
        }

        return commonResponsePojo;
    }


    @Transactional
    @Override
    public void deleteProduct(String productSKU) throws Exception {

        ProductEntity productEntity = productRepository.findBySKU(productSKU);

        if(!StringUtils.isEmpty(productEntity.getThumbnailImagePath())) {
            s3ClientFactory.deleteImageFromS3(s3BucketName, productEntity.getThumbnailImagePath());
        }

        if(!StringUtils.isEmpty(productEntity.getModelImagePath())) {
            s3ClientFactory.deleteImageFromS3(s3BucketName, productEntity.getModelImagePath());
        }

        if(!StringUtils.isEmpty(productEntity.getRealImagePath())) {
            s3ClientFactory.deleteImageFromS3(s3BucketName, productEntity.getRealImagePath());
        }

        productRepository.deleteBySKU(productSKU);

    }

    @Override
    public CommonResponsePojo updateProduct(ProductCreateUpdatePojo createUpdatePojo) throws Exception {
        CommonResponsePojo commonResponsePojo = new CommonResponsePojo();

        ProductEntity productEntity = productRepository.findBySKU(createUpdatePojo.getStockUnit());

        if(productEntity == null) {
            //TODO:: Throw Invalid Product Exception
            throw new InvalidRequestException("product doesn't exist by sku id:"+createUpdatePojo.getStockUnit());
        }

        if(createUpdatePojo.getName() != null) {
            productEntity.setName(createUpdatePojo.getName());
        }

        if(createUpdatePojo.getDescription() != null) {
            productEntity.setDescription(createUpdatePojo.getDescription());
        }

        if(createUpdatePojo.getActive() != null) {
            productEntity.setActive(createUpdatePojo.getActive());
        }

        if(createUpdatePojo.getPrice() != null) {
            productEntity.setPrice(createUpdatePojo.getPrice());
        }

        //getting product category id from request pojo
        if(createUpdatePojo.getCategory() != null && createUpdatePojo.getCategory().getCategoryId() != null) {
            productEntity.setCategoryId(new ProductCategoryEntity(createUpdatePojo.getCategory().getCategoryId()));
        }

        if (createUpdatePojo.getInventory() != null) {
            ProductInventoryEntity productInventoryEntity = new ProductInventoryEntity(createUpdatePojo.getInventory());
            productInventoryEntity.setModifiedAt(Timestamp.from(Instant.now()));

            productEntity.setInventoryId(productInventoryEntity);
        }

        if(createUpdatePojo.getThumbnailImage() != null && !createUpdatePojo.getThumbnailImage().isEmpty()) {
            s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getThumbnailImage());

            productEntity.setThumbnailImagePath(s3ImagePath);
        }

        if (createUpdatePojo.getModelImage() != null && !createUpdatePojo.getModelImage().isEmpty()) {
           s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getModelImage());

           productEntity.setModelImagePath(s3ImagePath);
        }

        if(createUpdatePojo.getRealImage() != null && !createUpdatePojo.getRealImage().isEmpty()) {
            s3ImagePath = s3ClientFactory.uploadImageToS3(s3BucketName, createUpdatePojo.getRealImage());

            productEntity.setRealImagePath(s3ImagePath);
        }

        productEntity = productRepository.save(productEntity);

        commonResponsePojo.setMessage("Product :"+createUpdatePojo.getName()+ "\t has been updated");

        return commonResponsePojo;
    }

    @Override
    public ProductListResponse getAllProductsByCategory(Integer categoryId) throws Exception {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductResponsePOJO> productList = new ArrayList<>();
        List<ProductEntity> productEntityList = productRepository.findByCategoryId(categoryId);
        if(!productEntityList.isEmpty()) {
            productEntityList.forEach(productEntity -> {
                ProductResponsePOJO product = new ProductResponsePOJO();
                product.setPid(productEntity.getId());
                product.setPname(productEntity.getName());
                product.setPSKU(productEntity.getSKU());
//            product.setCategory(new ProductCategoryPOJO(
//                    productEntity.getCategoryId().getName(),
//                    productEntity.getCategoryId().getParentId().getName()));
                product.setPrice(productEntity.getPrice());
                product.setActive(productEntity.getActive());
                product.setInventory(productEntity.getInventoryId().getQuantity());
                product.setCreationDate(productEntity.getCreatedAt());
                productList.add(product);
            });
        }
        productListResponse.setProductsList(productList);
        return productListResponse;
    }
}
