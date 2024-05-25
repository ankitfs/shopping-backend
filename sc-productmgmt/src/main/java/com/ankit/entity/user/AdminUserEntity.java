package com.ankit.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "adminuser")
public class AdminUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_user_generator")
    @SequenceGenerator(name="admin_user_generator", sequenceName = "adminuser_id_seq", allocationSize = 1)
    private Integer id;

    @Column
    private String username;

    @Column(name = "pwd")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "created_at")
    private Timestamp createdDate;

    @Column(name = "modified_at")
    private Timestamp modifiedDate;
}
