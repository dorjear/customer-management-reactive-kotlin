package com.dorjear.study.customer.repositories

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

import java.sql.Date
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.stream.StreamSupport

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import com.dorjear.study.customer.configuration.RepositoryConfiguration
import com.dorjear.study.customer.domain.Customer

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(RepositoryConfiguration::class))
class CustomerRepositoryTest {
    private var customerRepository: CustomertRepository? = null
    @Autowired
    fun setCustomerRepository(customerRepository: CustomertRepository) {
        this.customerRepository = customerRepository
    }

    @Test
    fun testSaveCustomer() {
        //setup customer
        val customer = Customer()
        customer.firstName = "Tom"
        customer.lastName = "Smith"
        val localDate = LocalDate.of(1999, Month.AUGUST, 12)
        customer.dateOfBirth = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        //save customer, verify has ID value after save
        assertNull(customer.id) //null before save
        customerRepository!!.save(customer)
        assertNotNull(customer.id) //not null after save
        //fetch from DB
        val fetchedCustomer = customerRepository!!.findById(customer.id!!).orElse(null)
        //should not be null
        assertNotNull(fetchedCustomer)
        //should equal
        assertEquals(customer.id, fetchedCustomer.id)
        assertEquals(customer.firstName, fetchedCustomer.firstName)
        //update description and save
        fetchedCustomer.firstName = "newFirstName"
        customerRepository!!.save(fetchedCustomer)
        //get from DB, should be updated
        val fetchedUpdatedCustomer = customerRepository!!.findById(fetchedCustomer.id!!).orElse(null)
        assertEquals(fetchedCustomer.firstName, fetchedUpdatedCustomer.firstName)
        //verify count of customers in DB
        val customerCount = customerRepository!!.count()
        assertEquals(customerCount, 1)
        //get all customers, list should only have one
        val customers = customerRepository!!.findAll()
        assertEquals(1, StreamSupport.stream(customers.spliterator(), false).count())
    }
}
