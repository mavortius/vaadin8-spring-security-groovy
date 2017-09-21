package com.company.demo.security

import groovy.transform.CompileStatic
import org.springframework.security.core.AuthenticationException

@CompileStatic
class SecurityContextNotFoundException extends AuthenticationException {

    SecurityContextNotFoundException(String msg, Throwable t) {
        super(msg, t)
    }

    SecurityContextNotFoundException(String msg) {
        super(msg)
    }
}
