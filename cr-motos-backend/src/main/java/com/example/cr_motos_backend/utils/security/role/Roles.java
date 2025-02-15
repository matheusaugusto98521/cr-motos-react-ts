package com.example.cr_motos_backend.utils.security.role;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    Roles(String role) {
        this.role = role;
    }
}
