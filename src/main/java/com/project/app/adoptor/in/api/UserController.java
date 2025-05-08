package com.project.app.adoptor.in.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
import com.project.app.domain.dto.UserDTO;
import com.project.app.domain.ports.UserUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserUsecase userUsecase;
    
    @Operation(summary = "Create a new user", description = "Adds a new user to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.create(dto));
    }
    
    @Operation(summary = "Update an existing user", description = "Updates the details of an existing user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.update(dto));
    }
    
    @Operation(summary = "Partially update an existing user", description = "Updates specific fields of an existing user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping
    public ResponseEntity<UserDTO> patch(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.patch(dto));
    }
    
    @Operation(summary = "Get user by ID", description = "Fetches the details of a user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt) {
        UserDTO dto = userUsecase.getById(id,jwt);
        return ResponseEntity.ok(dto);   
     }
    
     @Operation(summary = "List all users", description = "Retrieves a list of all users in the system")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
             content = @Content(schema = @Schema(implementation = UserDTO.class))),
         @ApiResponse(responseCode = "500", description = "Internal server error")
     })
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userUsecase.getAll());
    }
    
    @Operation(summary = "Get user by name", description = "Fetches the details of a user by its Name")
     @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
             content = @Content(schema = @Schema(implementation = UserDTO.class))),
         @ApiResponse(responseCode = "500", description = "Internal server error")
     })
    @GetMapping("/by-name")
    public ResponseEntity<List<UserDTO>> getByFirstname(@RequestParam String firstname) {
        return ResponseEntity.ok(userUsecase.getUsersByFirstName(firstname));
    }
    
    @Operation(summary = "List paged users", description = "Returns users with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paged users retrieved successfully",
            content = @Content(schema = @Schema(implementation = PagedResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<UserDTO>> getAllPaged(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userUsecase.getAllPaged(offset, limit));
    }
    
    @Operation(summary = "Delete user by ID", description = "Deletes a user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        userUsecase.delete(id);
        Map<String, String> response = Stream.of(new String[][] {
            { "status", "success" },
            { "message", "User deleted successfully" }
        })
        .collect(Collectors.toMap(data -> data[0], data -> data[1]));
        return ResponseEntity.ok(response);
    }
}
