package com.ankit.dao;

import com.ankit.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    //@Query(value = "select pe from ProductEntity pe inner join ProductCategoryEntity pce on pe.categoryId=pce.id")
    //@Query(value = "select pe from ProductEntity pe")
    //public List<ProductEntity> findAll();

    //@Query(nativeQuery = true, value = "delete from product p where p.sku = :product")
    //@Query(value = "delete from ProductEntity p where p.SKU = :product")
    void deleteBySKU(String SKU);

    ProductEntity findBySKU(String SKU);

    @Query(nativeQuery = true,value = "select pc.id,pc.name, pc.description, pc.sku, pc.category_id, pc.inventory_id, pc.price, pc.active, pc.created_at, pc.modified_at, pc.short_image, pc.model_image, pc.real_image from product pc join product_category pcat on pc.category_id = pcat.id where pcat.parent_id = :categoryId or pcat.id = :categoryId")
    //@Query(value = "select pce from ProductEntity pce where pce.categoryId.parentId.id = :categoryId")
    List<ProductEntity> findByCategoryId(@Param("categoryId") Integer categoryId);
}
