package com.ankit.dao;

import com.ankit.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    //@Query(value = "select pe from ProductEntity pe inner join ProductCategoryEntity pce on pe.categoryId=pce.id")
    //@Query(value = "select pe from ProductEntity pe")
    //public List<ProductEntity> findAll();
}