package com.ankit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_generator")
    @SequenceGenerator(name="products_generator", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    private String description;

    private String SKU;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProductCategoryEntity categoryId;

    //@OneToOne
    //@JoinColumn(name = "inventory_id", referencedColumnName = "id")
    //private ProductInventoryEntity inventoryId;

    private BigDecimal price;

    //Many products items associated with single discount item
    //@ManyToOne
    //@JoinColumn(name = "discount_id", referencedColumnName = "id")
    //private ProductDiscountEntity discount;

    private Boolean active;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
