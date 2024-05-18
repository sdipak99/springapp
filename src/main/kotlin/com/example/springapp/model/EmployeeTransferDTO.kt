package com.example.springapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable

class EmployeeTransferDTO constructor(@JsonIgnore val obj:Employee) : Serializable {
    val employeeId = obj.employeeId
    val employeeName= obj.usrname
    val employeeSex = obj.sex
    val employeeAddress = obj.address
}