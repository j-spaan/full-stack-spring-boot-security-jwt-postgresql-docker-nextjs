package com.example.backend.security.role;

import com.example.backend.payload.request.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
        ResponseEntity<List<RoleDto>> findAllRoles();
        ResponseEntity<RoleDto> findRoleById(int id);
        Role findRoleByName(String roleUser);
        ResponseEntity<RoleDto> saveRole(RoleDto roleDto);
        ResponseEntity<RoleDto> updateRoleById(int id, RoleDto roleDto);
        ResponseEntity<Void> deleteRoleById(int id);
}
