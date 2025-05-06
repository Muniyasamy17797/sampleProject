package com.project.app.domain.dto;

import java.io.Serializable;
import java.util.Set;

import com.project.app.infrastructure.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private  Long id;
    private  String username;
    private  String password;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String mobile;
    private  String address;
    private  String postalCode;
    private  Set<RoleType> roles;
    private  Set<String> rolesNames;
    private  Long rowVersion;
 
      
}
