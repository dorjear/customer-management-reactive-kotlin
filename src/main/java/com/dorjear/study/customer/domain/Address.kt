package com.dorjear.study.customer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

import com.fasterxml.jackson.annotation.JsonIgnore

import io.swagger.annotations.ApiModelProperty

@Entity
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated customer ID")
    @JsonIgnore
    var id: Int? = null
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the address")
    var version: Int? = null

    @ApiModelProperty(notes = "The first line")
    var line1: String? = null
    @ApiModelProperty(notes = "The second line")
    var line2: String? = null


}
