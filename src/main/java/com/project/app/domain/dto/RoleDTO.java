package com.project.app.domain.dto;

import java.time.Instant;

import com.project.app.infrastructure.enums.RoleType;

import lombok.Data;

@Data
public class RoleDTO {


    private final Long id;
    private final String name;
    private final Instant createdDate;
    private final String createdBy;
    private final Instant lastModifiedDate;
    private final String lastModifiedBy;
    private final RoleType roleType;
    
}
