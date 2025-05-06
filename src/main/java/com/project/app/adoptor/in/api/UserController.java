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


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserUsecase userUsecase;

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.create(dto));
    }
    
    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.update(dto));
    }

     @PatchMapping
    public ResponseEntity<UserDTO> patch(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userUsecase.patch(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id,@AuthenticationPrincipal Jwt jwt) {
        UserDTO dto = userUsecase.getById(id,jwt);
        return ResponseEntity.ok(dto);   
     }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userUsecase.getAll());
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<UserDTO>> getByFirstname(@RequestParam String firstname) {
        return ResponseEntity.ok(userUsecase.getUsersByFirstName(firstname));
    }

    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<UserDTO>> getAllPaged(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userUsecase.getAllPaged(offset, limit));
    }

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
