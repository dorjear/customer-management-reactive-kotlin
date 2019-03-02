package com.dorjear.study.customer.services

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.notNull

import java.time.Duration
import java.util.function.Function

import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.reactivestreams.Subscriber
import org.springframework.http.ResponseEntity
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate

import com.dorjear.study.customer.domain.Customer
import com.nhaarman.mockito_kotlin.mock
import org.mockito.Mockito.*
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers


/**
 *
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 */
class CustomerServiceRestfulImplMockTest {

    @Mock
    private val webClient: WebClient = mock()
    @Mock
    private val customer: Customer = mock()
    @Mock
    private val mockResponseEitity: ResponseEntity<List<Customer>> = mock()
    //    @Mock
    //    private Mono<ClientResponse> clientResponseMono;
    @Mock
    private val subscribeFunction: Function<ClientResponse, Mono<Customer>> = mock()
    @Mock
    private val uriSpecMock: WebClient.RequestHeadersUriSpec<*> = mock()
    @Mock
    private val bodyUriSpecMock: WebClient.RequestBodyUriSpec = mock()
    @Mock
    private val headersSpecMock: WebClient.RequestHeadersSpec<*> = mock()
    @Mock
    private val clientResponse: ClientResponse = mock()


    @InjectMocks
    private var customerServiceImpl: CustomerServiceRestfulImpl = mock()

    @Before
    fun setupMock() {
        MockitoAnnotations.initMocks(this)
        customerServiceImpl = CustomerServiceRestfulImpl()
        ReflectionTestUtils.setField(customerServiceImpl, "baseUrl", "http://host")
        ReflectionTestUtils.setField(customerServiceImpl, "detailUrl", "/detailUrl/")
        ReflectionTestUtils.setField(customerServiceImpl, "createUrl", "/createUrl/")
        ReflectionTestUtils.setField(customerServiceImpl, "deleteUrl", "/deleteUrl/")
    }

    private fun getWebClientMock(resp: Customer): WebClient {


        `when`(webClient.get()).thenReturn(uriSpecMock)
        `when`(uriSpecMock.uri(ArgumentMatchers.notNull<String>())).thenReturn(headersSpecMock)
        `when`(headersSpecMock.header(notNull(), notNull<String>())).thenReturn(headersSpecMock)
        `when`(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock)
        `when`(clientResponse.bodyToMono<Customer>(Customer::class.java)).thenReturn(Mono.just(customer))

        val clientResponseMono = Mono.just(clientResponse)
        `when`(headersSpecMock.exchange()).thenReturn(clientResponseMono)

        //        when(clientResponseMono.subscribeOn(any(Scheduler.class))).thenReturn(clientResponseMono);
        //        when(clientResponseMono.timeout(ArgumentMatchers.<Duration>notNull())).thenReturn(clientResponseMono);
        //        when(clientResponseMono.flatMap(ArgumentMatchers.<Function>notNull())).thenReturn(Mono.just(customer));
        return webClient
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnCustomer_whenGetCustomerByIdIsCalled() {
        // Arrange
        `when`(webClient.get()).thenReturn(uriSpecMock)
        `when`(uriSpecMock.uri(ArgumentMatchers.notNull<String>())).thenReturn(headersSpecMock)
        `when`(headersSpecMock.header(notNull(), notNull<String>())).thenReturn(headersSpecMock)
        `when`(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock)
        `when`(clientResponse.bodyToMono<Customer>(Customer::class.java)).thenReturn(Mono.just(customer!!))
        val clientResponseMono = Mono.just(clientResponse)
        `when`(headersSpecMock.exchange()).thenReturn(clientResponseMono)

        ReflectionTestUtils.setField(customerServiceImpl, "webClient", webClient)

        // Act
        val retrievedCustomer = customerServiceImpl!!.getCustomerById(5).block()
        // Assert
        assertThat(retrievedCustomer, `is`(equalTo(customer)))

        //TODO:
        //Verify
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnCustomer_whenSaveCustomerIsCalled() {
        // Arrange
        `when`(webClient!!.post()).thenReturn(bodyUriSpecMock)
        `when`(bodyUriSpecMock!!.uri(ArgumentMatchers.notNull<String>())).thenReturn(bodyUriSpecMock)
        `when`(bodyUriSpecMock.body(ArgumentMatchers.notNull())).thenReturn(headersSpecMock)
        `when`(headersSpecMock!!.header(notNull(), notNull())).thenReturn(headersSpecMock)
        `when`(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock)
        `when`<Mono<Customer>>(clientResponse!!.bodyToMono<Customer>(Customer::class.java)).thenReturn(Mono.just(customer!!))
        val clientResponseMono = Mono.just(clientResponse)
        `when`(headersSpecMock.exchange()).thenReturn(clientResponseMono)
        ReflectionTestUtils.setField(customerServiceImpl, "webClient", webClient)

        // Act
        val savedCustomer = customerServiceImpl!!.saveCustomer(customer).block()
        // Assert
        assertThat(savedCustomer, `is`(equalTo(customer)))

        //TODO:
        //Verify
    }

    @Test
    @Throws(Exception::class)
    fun shouldCallDeleteMethodOfCustomerRepository_whenDeleteCustomerIsCalled() {
        // Arrange
        `when`(webClient!!.delete()).thenReturn(uriSpecMock)
        `when`(uriSpecMock!!.uri(ArgumentMatchers.notNull<String>())).thenReturn(headersSpecMock)
        val clientResponseMono = Mono.just(clientResponse!!)
        `when`(headersSpecMock!!.exchange()).thenReturn(clientResponseMono)

        ReflectionTestUtils.setField(customerServiceImpl, "webClient", webClient)
        // Act
        customerServiceImpl!!.deleteCustomer(5)
        // Assert
        verify(webClient, times(1)).delete()
    }
}