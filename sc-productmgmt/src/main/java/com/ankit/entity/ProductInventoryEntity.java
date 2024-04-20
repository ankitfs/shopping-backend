package com.ankit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_inventory")
//This Table stores the inventory details of the products
public class ProductInventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pr_inventory_generator")
    @SequenceGenerator(name="pr_inventory_generator", sequenceName = "product_inventory_id_seq", allocationSize = 1)
    private Integer id;
    private Integer quantity;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    //@OneToOne(mappedBy = "inventoryId")
    //private ProductEntity product;
}
