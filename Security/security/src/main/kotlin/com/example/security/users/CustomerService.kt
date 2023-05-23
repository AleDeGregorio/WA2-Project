package com.example.security.users

import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {
}