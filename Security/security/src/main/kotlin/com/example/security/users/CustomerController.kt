package com.example.security.users

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController (private val customerService: CustomerService) {

    fun register(@RequestBody customerDTO: CustomerDTO) {
        val customer = Customer()
        customer.name
    }
}