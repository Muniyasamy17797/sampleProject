package com.project.app.adoptor.in.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.RoleDTO;
import com.project.app.domain.ports.RoleUsecase;

import jakarta.validation.Valid;

@RestController
public class RoleController {

    @Autowired
    private RoleUsecase roleUsecase;

     @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.create(dto));
    }

    @PutMapping
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.update(dto));
    }

     @PatchMapping
    public ResponseEntity<RoleDTO> patch(@RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.patch(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAll() {
        return ResponseEntity.ok(roleUsecase.getAll());
    }

     @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Long id) {
        return roleUsecase.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<RoleDTO>> getAllPaged(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(roleUsecase.getAllPaged(offset, limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleUsecase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
