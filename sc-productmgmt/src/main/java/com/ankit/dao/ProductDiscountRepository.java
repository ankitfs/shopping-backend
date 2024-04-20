package com.ankit.dao;

import com.ankit.entity.ProductDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscountEntity, Integer> {
}
