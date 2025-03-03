package com.example.demo.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER("USER") ,
    ADMIN("USER", "ADMIN") ;

    private String[] role;

    Role(String ...role){
        this.role = role;
    }
    public static Role getRole(String dbRoleName){
        return Arrays.stream(Role.values())
            .filter(R -> R.name().equals(dbRoleName))
            .findFirst().get();
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Stream.of(this.role)
            .map(r->new SimpleGrantedAuthority(r))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
