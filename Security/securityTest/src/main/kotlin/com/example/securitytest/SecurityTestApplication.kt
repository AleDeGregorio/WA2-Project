package com.example.securitytest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityTestApplication

fun main(args: Array<String>) {
	runApplication<SecurityTestApplication>(*args)
}
