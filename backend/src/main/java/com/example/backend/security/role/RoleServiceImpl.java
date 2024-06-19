package com.example.backend.security.role;

import com.example.backend.http.HttpRequestService;
import com.example.backend.payload.exception.ResourceNotFoundException;
import com.example.backend.payload.request.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record RoleServiceImpl(
        HttpRequestService httpRequestService,
        RoleMapperService roleMapperService,
        RoleRepository roleRepository) implements RoleService {

    @Override
    public ResponseEntity<List<RoleDto>> findAllRoles() {
        List<Role> roles = roleRepository.findAll();

        List<RoleDto> roleDtoList = roles.stream()
                .map(roleMapperService::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(roleDtoList);
    }

    @Override
    public ResponseEntity<RoleDto> findRoleById(int id) {
        return ResponseEntity.ok().body(
                roleMapperService.convertToDto(this.findById(id))
        );
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
    public ResponseEntity<RoleDto> saveRole(RoleDto roleDto) {
        Role newRole = roleMapperService.convertToEntity(roleDto);
        return ResponseEntity.created(
                URI.create(httpRequestService.getRequestUri()))
                .body(roleMapperService.convertToDto(roleRepository.save(newRole)));
    }

    @Override
    public ResponseEntity<RoleDto> updateRoleById(int id, RoleDto roleDto) {
        Role updateRole = this.findRoleByName(roleDto.getName());
        updateRole.setName(roleDto.getName());
        return ResponseEntity.ok().body(
                roleMapperService.convertToDto(
                        roleRepository.save(updateRole)
                ));
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