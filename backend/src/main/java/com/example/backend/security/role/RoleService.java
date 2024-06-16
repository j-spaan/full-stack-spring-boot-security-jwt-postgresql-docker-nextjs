package com.example.backend.security.role;

import com.example.backend.payload.request.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
        List<Role> findAllRoles();
        ResponseEntity<Role> findRoleById(int id);
        Role findRoleByName(String roleUser);
        ResponseEntity<Role> saveRole(RoleDto roleDto);
        ResponseEntity<Role> updateRoleById(int id, RoleDto roleDto);
        ResponseEntity<Void> deleteRoleById(int id);
}
