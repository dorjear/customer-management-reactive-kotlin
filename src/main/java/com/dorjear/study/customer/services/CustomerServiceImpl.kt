package com.dorjear.study.customer.services

import java.util.stream.Collectors
import java.util.stream.StreamSupport

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.dorjear.study.customer.domain.Customer
import com.dorjear.study.customer.repositories.CustomertRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CustomerServiceImpl : CustomerService {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private var customerRepository: CustomertRepository? = null

    @Autowired
    fun setCustomerRepository(customerRepository: CustomertRepository) {
        this.customerRepository = customerRepository
    }

    override fun listAllCustomers(): Flux<Customer> {
        logger.debug("listAllCustomers called")
        return Flux.fromIterable(customerRepository!!.findAll())
    }

    override fun getCustomerById(id: Int): Mono<Customer> {
        logger.debug("getCustomerById called")
        return Mono.fromCallable { customerRepository!!.findById(id).orElse(null) }
    }

    override fun saveCustomer(customer: Customer): Mono<Customer> {
        logger.debug("saveCustomer called")
        return Mono.fromCallable { customerRepository!!.save(customer) }
    }

    override fun deleteCustomer(id: Int) {
        logger.debug("deleteCustomer called")
        customerRepository!!.deleteById(id)
    }
}
