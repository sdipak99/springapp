package com.example.springapp.controller

import com.example.springapp.config.JwtService
import com.example.springapp.service.EmployeeService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val employeeService: EmployeeService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
    ) {

    @PostMapping("/authenticate")
    fun authenticate (@RequestBody requestBody:AuthenticationRequest ):ResponseEntity<AuthenticationResponse>{
        var user = employeeService.getEmployee(requestBody.userName)
        var jwtToken = jwtService.generateJwt(user)
        return ResponseEntity.ok(AuthenticationResponse(jwtToken))
    }
}