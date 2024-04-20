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
@Entity(name = "product_discount")
public class ProductDiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pr_discount_generator")
    @SequenceGenerator(name="pr_discount_generator", sequenceName = "product_discount_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String description;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;
    private Boolean active;

    //Single Discount Item can be associated with multiple products
    //@OneToMany(mappedBy = "discount")
    //private ProductEntity product;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
