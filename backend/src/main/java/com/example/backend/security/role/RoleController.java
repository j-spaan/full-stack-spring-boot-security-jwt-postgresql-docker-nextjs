package com.example.backend.security.role;

import com.example.backend.config.AppConstants;
import com.example.backend.payload.request.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.Request.ROLES)
public record RoleController(RoleService roleService) {

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(int id) {
        return roleService.findRoleById(id);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRoleById(int id, RoleDto roleDto) {
        return roleService.updateRoleById(id, roleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(int id) {
        return roleService.deleteRoleById(id);
    }
}