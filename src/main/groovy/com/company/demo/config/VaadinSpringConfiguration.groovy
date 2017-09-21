package com.company.demo.config

import com.vaadin.spring.annotation.EnableVaadin
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy
import org.vaadin.spring.http.HttpService
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration
import org.vaadin.spring.security.shared.VaadinAuthenticationSuccessHandler
import org.vaadin.spring.security.shared.VaadinSessionClosingLogoutHandler
import org.vaadin.spring.security.shared.VaadinUrlAuthenticationSuccessHandler
import org.vaadin.spring.security.web.VaadinRedirectStrategy

@CompileStatic
@EnableVaadin
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@EnableVaadinSharedSecurity
@EnableJpaAuditing
class VaadinSpringConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService

    @Bean
    PasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder()
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.with {
            csrf().disable()
            authorizeRequests().
                    antMatchers('/login/**').anonymous().antMatchers('/vaadinServlet/UIDL/**').permitAll().
                    antMatchers('/vaadinServlet/HEARTBEAT/**').permitAll().
                    anyRequest().authenticated()
            httpBasic().disable()
            formLogin().disable()
            logout().addLogoutHandler(new VaadinSessionClosingLogoutHandler()).logoutUrl('/logout').
                    logoutSuccessUrl('/login?logout').permitAll()
            exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint('/login'))
            rememberMe().rememberMeServices(rememberMeServices()).key('myAppKey')
            sessionManagement().sessionAuthenticationStrategy(sessionManagementStrategy())
        }
    }

    @Override
    void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers('/VAADIN/**')
    }

    @Bean
    @Override
    AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean()
    }

    @Bean
    SessionAuthenticationStrategy sessionManagementStrategy() {
        new SessionFixationProtectionStrategy()
    }

    @Bean
    RememberMeServices rememberMeServices() {
        new TokenBasedRememberMeServices('myAppKey', userDetailsService)
    }

    @Bean(name = VaadinSharedSecurityConfiguration.VAADIN_AUTHENTICATION_SUCCESS_HANDLER_BEAN)
    VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(HttpService service, VaadinRedirectStrategy strategy) {
        new VaadinUrlAuthenticationSuccessHandler(service, strategy, '/')
    }

}
