package com.example.demo.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("USER", "ADMIN");

    private final String[] role;

    Role(String... role) {
        this.role = role;
    }

    public static Role getRole(String dbRoleName) {
        return Arrays.stream(Role.values())
                .filter(R -> R.name().equals(dbRoleName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid role: " + dbRoleName));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
