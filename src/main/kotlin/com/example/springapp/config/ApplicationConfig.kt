package com.example.springapp.config

import com.example.springapp.service.EmployeeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class ApplicationConfig(val employeeService: EmployeeService) {
    @Bean
    fun userDetailService():UserDetailsService{
        return UserDetailsService { username -> employeeService.getEmployee(username) }
    }

    @Bean
    fun authenticationProvider():AuthenticationProvider{
        return DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailService())
            setPasswordEncoder(setPasswordEncoder())
        }
    }

    @Bean
    fun authenticationManager(config:AuthenticationConfiguration):AuthenticationManager{
        return config.authenticationManager
    }

    @Bean
    fun setPasswordEncoder():BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


}