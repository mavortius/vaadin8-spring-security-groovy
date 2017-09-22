package com.company.demo.security

import groovy.transform.CompileStatic
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@CompileStatic
class SecurityUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        super.getRoles().collect { new SimpleGrantedAuthority(it.getType().name()) }
    }

    @Override
    String getUsername() {
        super.username
    }

    @Override
    String getPassword() {
        super.password
    }

    @Override
    boolean isAccountNonExpired() {
        true
    }

    @Override
    boolean isAccountNonLocked() {
        true
    }

    @Override
    boolean isCredentialsNonExpired() {
        true
    }

    @Override
    boolean isEnabled() {
        true
    }
}
