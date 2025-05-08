package com.project.app.adoptor.in.api;

import java.util.List;

import javax.management.relation.RoleInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.RoleDTO;
import com.project.app.domain.ports.RoleUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleUsecase roleUsecase;
    
    @Operation(summary = "Create a new role", description = "Adds a new role to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role created successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.create(dto));
    }
    
    @Operation(summary = "Update an existing role", description = "Updates the details of an existing role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role input"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.update(dto));
    }
    
    @Operation(summary = "Partially update an existing role", description = "Updates specific fields of an existing role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role input"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping
    public ResponseEntity<RoleDTO> patch(@RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleUsecase.patch(dto));
    }
    
    @Operation(summary = "List all roles", description = "Retrieves a list of all roles in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles retrieved successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAll() {
        return ResponseEntity.ok(roleUsecase.getAll());
    }
    
    @Operation(summary = "Get role by ID", description = "Fetches the details of a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role retrieved successfully",
            content = @Content(schema = @Schema(implementation = RoleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Long id) {
        return roleUsecase.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "List paged roles", description = "Returns roles with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paged roles retrieved successfully",
            content = @Content(schema = @Schema(implementation = PagedResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<RoleDTO>> getAllPaged(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(roleUsecase.getAllPaged(offset, limit));
    }
    
    @Operation(summary = "Delete role by ID", description = "Deletes a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleUsecase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
