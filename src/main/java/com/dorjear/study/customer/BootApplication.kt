package com.dorjear.study.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootApplication

fun main(args: Array<String>) {
    runApplication<BootApplication>(*args)
}
