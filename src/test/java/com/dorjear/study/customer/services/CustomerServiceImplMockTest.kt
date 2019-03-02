package com.dorjear.study.customer.services

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

import java.util.Optional

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import com.dorjear.study.customer.domain.Customer
import com.dorjear.study.customer.repositories.CustomertRepository


class CustomerServiceImplMockTest {

    private var customerServiceImpl: CustomerServiceImpl? = null
    @Mock
    private val customerRepository: CustomertRepository? = null
    @Mock
    private val customer: Customer? = null

    @Before
    fun setupMock() {
        MockitoAnnotations.initMocks(this)
        customerServiceImpl = CustomerServiceImpl()
        customerServiceImpl!!.setCustomerRepository(customerRepository!!)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnCustomer_whenGetCustomerByIdIsCalled() {
        // Arrange
        `when`(customerRepository!!.findById(5)).thenReturn(Optional.of(customer!!))
        // Act
        val retrievedCustomer = customerServiceImpl!!.getCustomerById(5).block()
        // Assert
        assertThat(retrievedCustomer, `is`(equalTo(customer)))

    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnCustomer_whenSaveCustomerIsCalled() {
        // Arrange
        `when`(customerRepository!!.save<Customer>(customer!!)).thenReturn(customer)
        // Act
        val savedCustomer = customerServiceImpl!!.saveCustomer(customer!!).block()
        // Assert
        assertThat(savedCustomer, `is`(equalTo<Customer>(customer)))
    }

    @Test
    @Throws(Exception::class)
    fun shouldCallDeleteMethodOfCustomerRepository_whenDeleteCustomerIsCalled() {
        // Arrange
        doNothing().`when`<CustomertRepository>(customerRepository).deleteById(5)
        //        Mockito.mock(CustomertRepository.class);
        // Act
        customerServiceImpl!!.deleteCustomer(5)
        // Assert
        verify<CustomertRepository>(customerRepository, times(1)).deleteById(5)
    }
}