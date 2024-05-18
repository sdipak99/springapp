package com.example.springapp.controller

import com.example.springapp.model.Employee
import com.example.springapp.model.EmployeeTransferDTO
import com.example.springapp.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class EmployeeController(private val service: UserDetailsService) {

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> {
        return ResponseEntity("Could not find the user", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/emp{empId}")
    fun getEmployee(@RequestParam empId:String):ResponseEntity<EmployeeTransferDTO> {
            val user = service.loadUserByUsername(empId)
            val data = user?.let {
                EmployeeTransferDTO(it as Employee)
            }
            return ResponseEntity.ok(data)
    }


}