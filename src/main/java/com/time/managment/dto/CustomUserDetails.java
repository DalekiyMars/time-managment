package com.time.managment.dto;

import com.time.managment.entity.SecurityUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final SecurityUser user;

    public CustomUserDetails(SecurityUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Предполагается, что у SecurityUser есть метод getRoles(), который возвращает Set<Role>
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // или реализуйте свою логику
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // или реализуйте свою логику
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // или реализуйте свою логику
    }

    @Override
    public boolean isEnabled() {
        return true; // или реализуйте свою логику
    }

}
