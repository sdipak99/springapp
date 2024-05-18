package com.example.springapp.model

import com.example.springapp.service.EmployeeService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CommandLineAppStartupRunner(
    private val empService: EmployeeService)
    :CommandLineRunner  {
    override fun run(vararg args: String?) {
        args.forEach { arg ->
            val empInfo = arg?.split(":")
            empInfo?.let {
                if(it.size == 5){
                    empService.addEmployee(
                        Employee(
                            usrname = it[0],
                            pwd = it[1],
                            employeeId = it[2],
                            sex = it[3],
                            address = it[4]
                            )
                    )
                }


            }

        }
    }
}