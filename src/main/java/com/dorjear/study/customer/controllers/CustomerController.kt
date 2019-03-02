package com.dorjear.study.customer.controllers

import com.dorjear.study.customer.domain.Customer
import com.dorjear.study.customer.services.CustomerService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AuthorizationServiceException
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import reactor.core.publisher.Mono.fromRunnable

@RestController
@RequestMapping("/customer")
@Api(value = "customer-management", description = "Operations pertaining to customers")
@ApiResponses(value = *arrayOf(ApiResponse(code = 200, message = "Successfully retrieved list"), ApiResponse(code = 401, message = "You are not authorized to view the resource"), ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), ApiResponse(code = 404, message = "The resource you were trying to reach is not found")))
class CustomerController {

    private var customerService: CustomerService? = null

    @Autowired
    fun setcustomerService(customerService: CustomerService) {
        this.customerService = customerService
    }

    @ApiOperation(value = "View a list of available customers")
    @RequestMapping(value = "/list", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json"))
    fun list(): Flux<Customer> {
        return customerService!!.listAllCustomers()
    }

    @ApiOperation(value = "Search a customer with an ID", response = Customer::class)
    @RequestMapping(value = "/show/{id}", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json"))
    fun showcustomer(@PathVariable id: Int, authentication: Authentication): Mono<Customer> {
        return customerService!!.getCustomerById(id).doOnNext { c -> verify(authentication, c) }
    }


    private fun verify(authentication: Authentication?, customer: Customer?) {
        if (authentication == null || customer == null) return
        val userDetails = authentication.principal as String
        if (authentication.authorities.stream().noneMatch { auth -> "ROLE_ADMIN" == auth.authority } && customer.customerId != userDetails) throw AuthorizationServiceException("Not authorized")
    }

    @ApiOperation(value = "Add a customer")
    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST), produces = arrayOf("application/json"))
    fun saveCustomer(@RequestBody customer: Customer): Mono<ResponseEntity<String>> {
        return customerService!!.saveCustomer(customer).map { newCust -> ResponseEntity("Customer saved successfully", HttpStatus.OK) }
    }

    @ApiOperation(value = "Update a customer")
    @RequestMapping(value = "/update/{id}", method = arrayOf(RequestMethod.PUT), produces = arrayOf("application/json"))
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer, authentication: Authentication): Mono<ResponseEntity<String>> {
        return customerService!!.getCustomerById(id).flatMap { storedCustomer ->
            verify(authentication, storedCustomer)
            storedCustomer.firstName = customer.firstName
            storedCustomer.lastName = customer.lastName
            storedCustomer.dateOfBirth = customer.dateOfBirth
            customerService!!.saveCustomer(storedCustomer).map { newCust -> ResponseEntity("Customer updated successfully", HttpStatus.OK) }
        }
    }

    @ApiOperation(value = "Delete a customer")
    @RequestMapping(value = "/delete/{id}", method = arrayOf(RequestMethod.DELETE), produces = arrayOf("application/json"))
    fun delete(@PathVariable id: Int): Mono<ResponseEntity<String>> {
        return fromRunnable<Any> { customerService!!.deleteCustomer(id) }.map { aVoid -> ResponseEntity("Customer deleted successfully", HttpStatus.OK) }
    }

}
