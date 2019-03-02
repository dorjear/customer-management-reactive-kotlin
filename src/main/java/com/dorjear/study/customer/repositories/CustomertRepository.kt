package com.dorjear.study.customer.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

import com.dorjear.study.customer.domain.Customer

@RepositoryRestResource
interface CustomertRepository : CrudRepository<Customer, Int>
