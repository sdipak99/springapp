package com.example.springapp.service

import com.example.springapp.model.Employee
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class EmployeeService {
    private val empList = mutableListOf<Employee>()

    fun addEmployee(empData : Employee){
        empList.add(empData)
    }

    fun getEmployee(empId:String):Employee{
        return empList.first {
            it.employeeId == empId
        }
    }

    fun getAllEmployees():List<Employee> = empList

}