package com.project.app.domain.service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.app.adoptor.out.UserRepository;
import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.UserDTO;
import com.project.app.domain.mapper.UserMapper;
import com.project.app.domain.model.Role;
import com.project.app.domain.model.Users;
import com.project.app.domain.ports.UserRepositoryPort;
import com.project.app.domain.ports.UserUsecase;
import com.project.app.adoptor.out.RoleRepository;
import com.project.app.infrastructure.enums.RoleType;
import com.project.app.infrastructure.utils.PasswordUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;




@Service
@Transactional
@Slf4j
public class UserService implements UserUsecase  {
    

    private final UserMapper userMapper;
    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepository roleRepository;

    public UserService(UserMapper userMapper, UserRepositoryPort userRepositoryPort, RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepositoryPort = userRepositoryPort;
        this.roleRepository = roleRepository;
    }
   
    
    @Override
    public UserDTO create(UserDTO dto) {

        var user = userMapper.toEntity(dto);
        user.setRoles(resolveRoles(dto.getRoles()));
        return userMapper.toDto(userRepositoryPort.save(user));
    }


     @Override
    public UserDTO update(UserDTO dto) {
        var user = userRepositoryPort.findById(dto.getId()).orElseThrow();
        System.out.println("user " + user);
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setAddress(dto.getAddress());
        user.setPostalCode(dto.getPostalCode());
        user.setRoles(resolveRoles(dto.getRoles()));
        log.info("updated user " + user);
        return userMapper.toDto(userRepositoryPort.save(user));
    }

    @Override
    public UserDTO patch(UserDTO dto) {

        var user = userRepositoryPort.findById(dto.getId()).orElseThrow();
        Optional.ofNullable(dto.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(dto.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(dto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(dto.getMobile()).ifPresent(user::setMobile);
        Optional.ofNullable(dto.getAddress()).ifPresent(user::setAddress);
        Optional.ofNullable(dto.getPostalCode()).ifPresent(user::setPostalCode);
        Optional.ofNullable(dto.getPassword()).ifPresent(PasswordUtil::hashPassword);
        Optional.ofNullable(dto.getRoles()).ifPresent(this::resolveRoles);

        return userMapper.toDto(userRepositoryPort.save(user));
    }

    @Override
public UserDTO getById(Long id) {
    var user = userRepositoryPort.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    return userMapper.toDto(user);
}

    @Override
    public List<UserDTO> getAll() {
        List<Users> users = userRepositoryPort.findAll();
        return users.stream()
            .map(userMapper::toDto)
            .toList();
        
    }

    @Override
    public PagedResponse<UserDTO> getAllPaged(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        Page<Users> page = userRepositoryPort.findAll(pageRequest);

        List<UserDTO> userInfos = page.getContent().stream()
            .map(userMapper::toDto)
            .toList();

        return new PagedResponse<>(
            userInfos,
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public void delete(Long id) {
        userRepositoryPort.deleteById(id);
    }

     private Set<Role> resolveRoles(Set<RoleType> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return new HashSet<>();
        };

        // Convert role names to RoleType enum and fetch corresponding Role entities
        return roleNames.stream()
            .map(roleName -> {
                var roleType = RoleType.valueOf(roleName.name().toUpperCase()); // Convert to enum
                return roleRepository.findByName(roleType.name()) // Fetch Role entity by name
                    .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleName));
            })
            .collect(Collectors.toSet());
    }
    
}
