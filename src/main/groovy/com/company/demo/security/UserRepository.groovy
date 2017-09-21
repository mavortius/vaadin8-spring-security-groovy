package com.company.demo.security

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}