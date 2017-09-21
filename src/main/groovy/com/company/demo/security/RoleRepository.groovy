package com.company.demo.security

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByUserId(String userId)
}