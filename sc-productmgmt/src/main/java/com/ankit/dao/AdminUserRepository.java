package com.ankit.dao;

import com.ankit.entity.user.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Integer> {

    Optional<AdminUserEntity> findByUsername(String userName) throws UsernameNotFoundException;
}
