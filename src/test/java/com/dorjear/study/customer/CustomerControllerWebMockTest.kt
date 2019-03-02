package com.dorjear.study.customer

import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc

import com.dorjear.study.customer.controllers.CustomerController
import com.dorjear.study.customer.domain.Customer
import com.dorjear.study.customer.services.CustomerService
import org.springframework.test.web.reactive.server.EntityExchangeResult
import reactor.core.publisher.Mono
import java.util.function.Consumer

@RunWith(SpringRunner::class)
@WebFluxTest(CustomerController::class)
//@WithMockUser(username = "ram", roles={"ADMIN"})
class CustomerControllerWebMockTest {

    @Autowired
    private val webTestClient: WebTestClient? = null

    @MockBean
    private val service: CustomerService? = null

    @Test
    @Throws(Exception::class)
    fun testGetCustomer() {
        val customer = Customer()
        customer.firstName = "first"
        customer.id = 1
        val expectJson = "{\"version\":null,\"id\":1,\"customerId\":null,\"firstName\":\"first\",\"lastName\":null,\"dateOfBirth\":null,\"homeAddress\":null,\"postalAddress\":null,\"workAddress\":null}"
        `when`(service!!.getCustomerById(1)).thenReturn(Mono.just(customer))
        webTestClient!!.get().uri("/customer/show/1").exchange().expectStatus().is2xxSuccessful.expectBody().consumeWith(Consumer<EntityExchangeResult<ByteArray>> { print(it) }).json(expectJson)
    }

    @Test
    @Throws(Exception::class)
    fun testListCustomer() {
        val customer = Customer()
        customer.firstName = "first"
        `when`(service!!.getCustomerById(1)).thenReturn(Mono.just(customer))
        webTestClient!!.get().uri("/customer/list").exchange().expectStatus().is2xxSuccessful.expectBody().consumeWith(Consumer<EntityExchangeResult<ByteArray>> { print(it) }).json("[]")
    }

    //TODO: Other test cases

}