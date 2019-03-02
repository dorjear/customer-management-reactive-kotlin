package com.dorjear.study.customer.services

import java.time.Duration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

import com.dorjear.study.customer.domain.Customer
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

/**
 *
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 */

//@Service("customerServiceRestful")
class CustomerServiceRestfulImpl : CustomerService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${crms.baseUrl}")
    private val baseUrl: String = ""
    @Value("\${crms.listUrl}")
    private val listUrl: String = ""
    @Value("\${crms.detailUrl}")
    private val detailUrl: String = ""
    @Value("\${crms.createUrl}")
    private val createUrl: String = ""
    @Value("\${crms.updateUrl}")
    private val updateUrl: String = ""
    @Value("\${crms.deleteUrl}")
    private val deleteUrl: String = ""

    private val webClient = WebClient.builder().baseUrl(baseUrl).build()

    override fun listAllCustomers(): Flux<Customer> {
        logger.debug("listAllCustomers called")
        return webClient.get().uri(baseUrl!! + listUrl!!)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMapMany { r -> r.bodyToFlux<Customer>(Customer::class.java) }
    }

    override fun getCustomerById(id: Int): Mono<Customer> {
        logger.debug("getCustomerById called")
        return webClient.get().uri(baseUrl + detailUrl + id)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMap { r -> r.bodyToMono<Customer>(Customer::class.java) }
    }

    override fun saveCustomer(customer: Customer): Mono<Customer> {
        logger.debug("saveCustomer called")
        return webClient.post().uri(baseUrl!! + createUrl!!).body(BodyInserters.fromObject(customer))
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMap { r -> r.bodyToMono<Customer>(Customer::class.java) }
    }

    override fun deleteCustomer(id: Int) {
        logger.debug("deleteCustomer called")
        webClient.delete().uri(baseUrl + deleteUrl + id)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
    }
}
