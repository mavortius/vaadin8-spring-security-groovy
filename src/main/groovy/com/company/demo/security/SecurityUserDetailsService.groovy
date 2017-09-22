package com.company.demo.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository repository

    SecurityUserDetailsService(UserRepository repository) {
        this.repository = repository
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = repository.findByUsername(username)

        if(user) {
            def props = user.properties.findAll { prop, val -> prop != 'class' }
            new SecurityUserDetails(props)
        } else {
            throw new UsernameNotFoundException("Username not found: $username")
        }
    }
}
