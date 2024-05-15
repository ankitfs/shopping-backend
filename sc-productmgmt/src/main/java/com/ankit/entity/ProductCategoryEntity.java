package com.ankit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_category")
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pr_category_generator")
    @SequenceGenerator(name="pr_category_generator", sequenceName = "product_category_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String description;
    private Boolean active;

    //Mapping to define the multiple categories associated with single parent category
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ProductCategoryEntity parentId;
    //@Column(name = "parent_id")
    //private Integer parentId;

    //Commenting the OneToMany Annotations since lot of hibernate queries being executed

    //Mapping to get list of categories with a single parentId
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentId")
    //private Set<ProductCategoryEntity> children;

    //Mapping to get list of products associated with a single categoryid
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryId")
    //private Set<ProductEntity> products;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "category_level")
    private Integer level;

    //Below Constructor used for return ProductCategoryEntity bean for Parent of Current Category
    public ProductCategoryEntity (Integer id) {
        this.id = id;
    }
}
