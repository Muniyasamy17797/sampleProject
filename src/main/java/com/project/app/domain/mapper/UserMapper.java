package com.project.app.domain.mapper;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.project.app.domain.dto.UserDTO;
import com.project.app.domain.model.Role;
import com.project.app.domain.model.Users;
import com.project.app.infrastructure.enums.RoleType;

@Mapper(componentModel = "spring")
public interface UserMapper {
  
  
  @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToRoleTypes")
  UserDTO toDto(Users user);

  @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleTypesToRoles")
  Users toEntity(UserDTO dto);

  default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
            .map(Role::getName) // Assuming Role has a getName() method
            .collect(Collectors.toSet());
    }
    @Named("mapRolesToRoleTypes")
    default Set<RoleType> mapRolesToRoleTypes(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return new HashSet<>();
        }
        return roles.stream()
            .map(role -> RoleType.valueOf(role.getName())) // Assuming RoleType has a valueOf method
            .collect(Collectors.toSet());
    }

    @Named("mapRoleTypesToRoles")
    default Set<Role> mapRoleTypesToRoles(Set<RoleType> roleTypes) {
        if (roleTypes == null || roleTypes.isEmpty()) {
            return new HashSet<>();
        }
        return roleTypes.stream()
            .map(roleType -> {
                Role role = new Role();
                role.setName(roleType.name());
                return role;
            })
            .collect(Collectors.toSet());
    }
    
}
