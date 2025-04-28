package com.project.app.domain.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.project.app.domain.dto.RoleDTO;
import com.project.app.domain.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    
    // Map RoleDTO to Role
    Role toEntity(RoleDTO dto);

    // Map Role to RoleInfo
    RoleDTO toDto(Role role);

    // Update existing Role entity with RoleUpdateDto
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RoleDTO dto, @MappingTarget Role entity);
}
