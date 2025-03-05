package com.example.demo.entity;

import com.example.demo.type.Role;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String oAuthId;

    @Column(nullable = false)
    private String oAuthPlatform;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String uuid;

    @CreatedDate
    private Date createdDateTime;

    @Column
    private Date deletedDateTime;

    @Builder
    public UserEntity(String oAuthId, String oAuthPlatform, String name, String email, String phone, String role, String uuid) {
        this.oAuthId = oAuthId;
        this.oAuthPlatform = oAuthPlatform;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.uuid = uuid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.getRole(role).getAuthorities();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    // 사용할 일이 없음
    @Override
    public String getPassword() {
        return null;
    }
}

