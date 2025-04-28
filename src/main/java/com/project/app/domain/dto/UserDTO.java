package com.project.app.domain.dto;

import java.util.Set;

import com.project.app.infrastructure.enums.RoleType;

import lombok.Data;

@Data
public class UserDTO {
    
    private final Long id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String mobile;
    private final String address;
    private final String postalCode;
    private final Set<RoleType> roles;
      
}
