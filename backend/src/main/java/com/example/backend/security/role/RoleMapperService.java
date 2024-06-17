package com.example.backend.security.role;

import com.example.backend.payload.request.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public record RoleMapperService(ModelMapper modelMapper) {

    public RoleDto convertToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public Role convertToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}