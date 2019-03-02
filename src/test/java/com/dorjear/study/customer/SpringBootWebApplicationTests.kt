package com.dorjear.study.customer

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration

import com.dorjear.study.customer.BootApplication


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(BootApplication::class))
@WebAppConfiguration
class SpringBootWebApplicationTests {

    @Test
    fun contextLoads() {
    }

}
