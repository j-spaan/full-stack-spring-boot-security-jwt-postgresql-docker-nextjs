package com.example.backend.security.role;

import com.example.backend.config.AppConstants;
import com.example.backend.payload.request.RoleDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.Request.ROLES)
public record RoleController(RoleService roleService) {

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRoleById(int id) {
        return roleService.findRoleById(id);
    }

    @PostMapping
    public Role createRole(RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @PutMapping("/{id}")
    public Role updateRole(int id, RoleDto roleDto) {
        return roleService.updateRole(id, roleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(int id) {
        roleService.deleteRole(id);
    }
}
