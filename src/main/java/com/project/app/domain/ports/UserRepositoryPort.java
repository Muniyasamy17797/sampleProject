package com.project.app.domain.ports;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.app.domain.model.Users;

public interface UserRepositoryPort {

    Users save(Users user);
    Optional<Users> findById(Long id);
    List<Users> findAll();
    Page<Users> findAll(Pageable pageable);
    void deleteById(Long id);
    
}
