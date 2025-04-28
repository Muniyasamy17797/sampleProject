package com.project.app.domain.ports;

import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleInfo;

import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.RoleDTO;

import jakarta.validation.Valid;

public interface RoleUsecase {


    RoleDTO create(@Valid RoleDTO roleDto);

    RoleDTO update(RoleDTO roleDto);

    RoleDTO patch(RoleDTO roleDto);

    Optional<RoleDTO> getById(Long id);

    List<RoleDTO> getAll();

    PagedResponse <RoleDTO> getAllPaged(int offset, int limit);

    void delete(Long id);
    
}
