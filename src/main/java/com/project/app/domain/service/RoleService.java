package com.project.app.domain.service;

import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.app.adoptor.out.RoleRepository;
import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.RoleDTO;
import com.project.app.domain.mapper.RoleMapper;
import com.project.app.domain.model.Role;
import com.project.app.domain.ports.RoleRepositoryPort;
import com.project.app.domain.ports.RoleUsecase;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class RoleService implements RoleUsecase {


    private final RoleRepositoryPort roleRepositoryPort;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepositoryPort roleRepositoryPort, RoleMapper roleMapper) {
        this.roleRepositoryPort = roleRepositoryPort;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO create(@Valid RoleDTO dto) {
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(roleRepositoryPort.save(role));
    }

    @Override
    public RoleDTO update(RoleDTO dto) {
        Role role = roleRepositoryPort.findById(dto.getId()).orElseThrow();
        role.setName(dto.getRoleType().name());
        return roleMapper.toDto(roleRepositoryPort.save(role));
    }

    @Override
    public RoleDTO patch(RoleDTO dto) {
        Role role = roleRepositoryPort.findById(dto.getId()).orElseThrow();
        if (dto.getName() != null) role.setName(dto.getRoleType().name());
        return roleMapper.toDto(roleRepositoryPort.save(role));
    }

    @Override
    public Optional<RoleDTO> getById(Long id) {
        return roleRepositoryPort.findById(id).map(roleMapper::toDto);
    }

    @Override
    public List<RoleDTO> getAll() {
        List<Role> roles = roleRepositoryPort.findAll();
       return roles.stream()
            .map(roleMapper::toDto)
            .toList();
    }

    public PagedResponse<RoleDTO> getAllPaged(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        Page<Role> page = roleRepositoryPort.findAll(pageRequest);

        List<RoleDTO> roleDTO = page.getContent().stream()
            .map(roleMapper::toDto)
            .toList();

        return new PagedResponse<>(
            roleDTO,
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }


    @Override
    public void delete(Long id) {
        roleRepositoryPort.deleteById(id);
    }

   

  
}
