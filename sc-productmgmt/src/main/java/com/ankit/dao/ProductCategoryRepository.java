package com.ankit.dao;

import com.ankit.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Integer> {

    //@Query(value = "select pce from ProductCategoryEntity pce where pce.name = :name and pce.parentId.name= :parent")
    @Query(nativeQuery = true, value = "select pc.id, pc.name, pc.description, pc.active, pc.created_at, pc.modified_at, pc.parent_id, pc.category_level from product_category pc where pc.name = :name and parent_id=(select id from product_category pc2 where name=:parent)")
    public ProductCategoryEntity findByChildAndParentCategoryName(@Param("name") String categoryName, @Param("parent") String parentCategoryName);

    @Query(nativeQuery = true, value = "select count(*) from product_category pc where pc.name = :name and pc.parent_id = :parentid and pc.category_level = :level")
    public Long isExists(@Param(value = "name") String categoryName, @Param(value = "parentid") Integer parentId, @Param(value = "level") Integer level);

    @Modifying
    @Query(nativeQuery = true, value = "delete from product_category pcr where pcr.id = :catid and pcr.category_level = :level")
    void deleteByIdandLevel(@Param("catid") Integer catid, @Param("level") Integer level);

    @Query(nativeQuery = true, value = "select pce.id, pce.name, pce.description, pce.active, pce.created_at, pce.modified_at, pce.parent_id, pce.category_level from product_category pce where pce.parent_id= :parentId ")
    public List<ProductCategoryEntity> findByParentId(@Param("parentId") Integer parentEntity);

    @Query(value = "select pc from product_category pc where pc.level = 0")
    public List<ProductCategoryEntity> findParentCategories();
}
