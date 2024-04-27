package com.ankit.dao;

import com.ankit.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Integer> {

    //@Query(value = "select pce from ProductCategoryEntity pce where pce.name = :name and pce.parentId.name= :parent")
    @Query(nativeQuery = true, value = "select pc.id, pc.name, pc.description, pc.active, pc.created_at, pc.modified_at, pc.parent_id from product_category pc where pc.name = :name and parent_id=(select id from product_category pc2 where name=:parent)")
    public ProductCategoryEntity findByChildAndParentCategoryName(@Param("name") String categoryName, @Param("parent") String parentCategoryName);

    @Query(nativeQuery = true, value = "select count(*) from product_category pc where pc.name = :name and pc.parent_id = :parentid and pc.level = :level")
    public boolean isExists(@Param(value = "name") String categoryName, @Param(value = "parentid") Integer parentId, @Param(value = "level") Integer level);

    @Query(value = "delete from ProductCategoryRepository pcr where pcr.id = :catid and pcr.level = :level")
    void deleteByIdandLevel(@Param("catid") Integer catid, @Param("level") Integer level);
}
