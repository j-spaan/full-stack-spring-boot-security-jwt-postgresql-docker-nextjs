package com.example.backend.security.role;

import com.example.backend.payload.request.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record RoleServiceImpl(RoleRepository roleRepository) implements RoleService {

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role saveRole(RoleDto roleDto) {
        Role saveRole = new Role();
        saveRole.setName(roleDto.getName());
        return roleRepository.save(saveRole);
    }

    @Override
    public Role updateRole(int id, RoleDto roleDto) {
        Role updateRole = roleRepository.findById(id).orElse(null);
        if (updateRole != null) {
            updateRole.setName(roleDto.getName());
            return roleRepository.save(updateRole);
        }
        return null;
    }

    @Override
    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}