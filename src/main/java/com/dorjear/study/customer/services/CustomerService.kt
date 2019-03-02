package com.dorjear.study.customer.services

import com.dorjear.study.customer.domain.Customer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerService {
    fun listAllCustomers(): Flux<Customer>

    fun getCustomerById(id: Int): Mono<Customer>

    fun saveCustomer(customer: Customer): Mono<Customer>

    fun deleteCustomer(id: Int)
}
