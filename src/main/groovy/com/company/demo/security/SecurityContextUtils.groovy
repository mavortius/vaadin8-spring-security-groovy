package com.company.demo.security

import groovy.transform.CompileStatic
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

@CompileStatic
class SecurityContextUtils implements Serializable {
    private static final Long serialVersionUID = 1L

    static boolean isLoggedIn() {
        Authentication authentication = authentication()

        if(!authentication) {
            false
        }

        authentication.authenticated
    }

    static boolean hasRole(RoleType type) {
        Authentication authentication = authentication()

        if(!authentication) {
            false
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(type.name())

        authentication.getAuthorities().contains(authority)
    }

    static User getUser() {
        Authentication authentication = authentication()

        if(!authentication) {
            new User()
        }

        authentication.getPrincipal() as User
    }

    static List<RoleType> getRoleTypes() {
        Authentication authentication = authentication()

        if(!authentication) {
            new ArrayList<>()
        }

        Collection<GrantedAuthority> authorities = authentication.authorities as Collection<GrantedAuthority>

        authorities.collect { RoleType.find(it.authority) } as List
    }

    private static Authentication authentication() {
        final SecurityContext securityContext = SecurityContextHolder.context

        if(!securityContext) {
            throw new SecurityContextNotFoundException('SecurityContext Not Found Exception')
        }

        final Authentication authentication = securityContext.authentication

        if(!authentication) {
            throw new SecurityContextNotFoundException('SecurityContext Authentication Not Found Exception')
        }

        authentication
    }
}
