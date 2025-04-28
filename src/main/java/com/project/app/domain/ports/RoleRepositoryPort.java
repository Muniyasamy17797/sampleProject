package com.project.app.domain.ports;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.project.app.domain.model.Role;


public interface RoleRepositoryPort {

    Role save(Role role);
    Optional<Role> findById(Long id);
    List<Role> findAll();
    Page<Role> findAll(Pageable pageable);
    void deleteById(Long id);
    
}
