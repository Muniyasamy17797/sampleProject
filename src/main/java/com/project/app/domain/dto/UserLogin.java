package com.project.app.domain.dto;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    
}
