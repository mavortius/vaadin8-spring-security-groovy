package com.company.demo.security

enum RoleType {

    ROLE_ADMIN, ROLE_USER

    static RoleType find(String type) {
        values().find { it.name() == type }
    }
}
