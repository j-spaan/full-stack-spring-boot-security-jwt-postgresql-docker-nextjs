package com.example.backend.security.role;

import com.example.backend.http.HttpRequestService;
import com.example.backend.payload.exception.ResourceNotFoundException;
import com.example.backend.payload.request.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public record RoleServiceImpl(
        HttpRequestService httpRequestService,
        RoleRepository roleRepository) implements RoleService {

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public ResponseEntity<Role> findRoleById(int id) {
        return ResponseEntity.ok().body(this.findById(id));
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "role.si.name.not.found",
                        new String[]{name})
                );
    }

    @Override
    public ResponseEntity<Role> saveRole(RoleDto roleDto) {
        Role saveRole = new Role();
        saveRole.setName(roleDto.getName());
        return ResponseEntity.created(
                URI.create(httpRequestService.getUri()))
                .body(roleRepository.save(saveRole));
    }

    @Override
    public ResponseEntity<Role> updateRoleById(int id, RoleDto roleDto) {
        Role updateRole = this.findById(id);
        if (updateRole != null) {
            updateRole.setName(roleDto.getName());
            return ResponseEntity.ok().body(roleRepository.save(updateRole));
        }
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRoleById(int id) {
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Role findById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "role.si.id.not.found",
                        new String[]{String.valueOf(id)})
                );
    }
}