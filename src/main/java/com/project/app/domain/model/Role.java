package com.project.app.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.annotation.Version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true, exclude = {"users"})
@Entity
@Data
public class Role extends AbstractAuditingEntity<Long> implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude // Prevent circular reference
    private Set<Users> users = new HashSet<>();

    @Version
    private Long rowVersion;


    
    public Long getId() {
        return id;
    }
    
}
