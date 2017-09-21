package com.company.demo

import com.company.demo.security.Role
import com.company.demo.security.RoleType
import com.company.demo.security.User
import com.company.demo.security.UserRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
import org.springframework.security.crypto.password.PasswordEncoder

@CompileStatic
@SpringBootApplication(exclude = SecurityAutoConfiguration)
class Application implements CommandLineRunner {

    @Autowired
    UserRepository userRepository

    @Autowired
    PasswordEncoder passwordEncoder

	static void main(String[] args) {
		SpringApplication.run Application, args
	}

    @Override
    void run(String... args) throws Exception {
        RoleType.values().each { RoleType r ->
            def name = r.name().split('_')[1].toLowerCase()

            User user = new User()
            user.username = name
            user.password = passwordEncoder.encode '1234'

            Role role = new Role()
            role.type = r
            role.user = user

            user.roles = [role] as List

            userRepository.save(user)
        }
    }
}
