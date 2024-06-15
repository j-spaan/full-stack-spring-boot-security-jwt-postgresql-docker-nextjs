package com.example.backend.security.role;

import com.example.backend.payload.request.RoleDto;

import java.util.List;

public interface RoleService {
        List<Role> findAllRoles();
        Role findRoleById(int id);
        Role findRoleByName(String roleUser);
        Role saveRole(RoleDto roleDto);
        Role updateRole(int id, RoleDto roleDto);
        void deleteRole(int id);
}
