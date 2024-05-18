package com.example.springapp.config

import com.example.springapp.model.Employee
import com.example.springapp.service.EmployeeService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService:JwtService,
    private val userDetailsService: UserDetailsService) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response)
            return
        }

        val jwt = authHeader.substring(7)
        val employeeId = jwtService.extractUsername(jwt)
        if(employeeId!=null && SecurityContextHolder.getContext().authentication == null){
            val userDetails = userDetailsService.loadUserByUsername(employeeId)
            if(jwtService.isJwtValid(jwt,userDetails)){
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities)

                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }else{
                //user is not available or the jwt is invalid
                throw UsernameNotFoundException("User not found")
            }
        }
        filterChain.doFilter(request,response)

    }
}