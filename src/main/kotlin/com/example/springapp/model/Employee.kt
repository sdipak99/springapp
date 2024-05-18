package com.example.springapp.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class Employee(var usrname:String,
               var pwd: String,
               var employeeId: String,
               var sex: String,
               var address: String,
    ):UserDetails{



    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf<GrantedAuthority>()
    }

    override fun getPassword() = pwd

    override fun getUsername() = employeeId

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

}
