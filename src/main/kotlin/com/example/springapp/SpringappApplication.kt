package com.example.springapp

import com.example.springapp.model.CommandLineAppStartupRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

	@SpringBootApplication
	class SpringappApplication

	fun main(args: Array<String>) {
		runApplication<SpringappApplication>(*args)
		{
			setAddCommandLineProperties(true)
		}
	}
