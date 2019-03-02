package com.dorjear.study.customer.bootstrap

import java.sql.Date
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

import com.dorjear.study.customer.domain.Address
import com.dorjear.study.customer.domain.Customer
import com.dorjear.study.customer.repositories.CustomertRepository

@Component
class SpringJpaBootstrap : ApplicationListener<ContextRefreshedEvent> {

    private var customerRepository: CustomertRepository? = null


    private val log = LogManager.getLogger(SpringJpaBootstrap::class.java)

    @Autowired
    fun setCustomerRepository(customerRepository: CustomertRepository) {
        this.customerRepository = customerRepository
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        loadCustomers()
    }

    private fun loadCustomers() {
        val tomSmith = Customer()
        tomSmith.firstName = "Tom"
        tomSmith.lastName = "Smith"
        val localDate = LocalDate.of(1988, Month.AUGUST, 12)
        tomSmith.dateOfBirth = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        tomSmith.customerId = "TomSmith"
        val tomAddress = Address()
        tomAddress.line1 = "Tom St"
        tomAddress.line2 = "Tom city"
        tomSmith.homeAddress = tomAddress
        tomSmith.postalAddress = tomAddress
        customerRepository!!.save(tomSmith)

        log.info("Saved tom - id: " + tomSmith.id!!)

        val davidSmith = Customer()
        davidSmith.firstName = "David"
        davidSmith.lastName = "Smith"
        val localDate2 = LocalDate.of(1999, Month.AUGUST, 12)
        davidSmith.dateOfBirth = Date.from(localDate2.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        davidSmith.customerId = "DavidSmith"
        val davidAddress = Address()
        davidAddress.line1 = "David St"
        davidAddress.line2 = "David city"
        davidSmith.homeAddress = davidAddress
        davidSmith.postalAddress = davidAddress
        customerRepository!!.save(davidSmith)

        log.info("Saved david - id:" + davidSmith.id!!)
    }


}



