package com.project.app.infrastructure.enums;

public enum RoleType {

    ADMIN,
    USER,
    MAINTAINER;

    public String toValue() {
        return this.name();
    }
    
}
