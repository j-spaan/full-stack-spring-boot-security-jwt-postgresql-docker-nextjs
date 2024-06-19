package com.example.backend.security.permission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findByName(String name);

    @Override
    void delete(Permission permission);
}