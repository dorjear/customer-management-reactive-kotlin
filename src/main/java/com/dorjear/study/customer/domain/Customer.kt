package com.dorjear.study.customer.domain

import java.util.Date

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Version

import com.fasterxml.jackson.annotation.JsonFormat

import io.swagger.annotations.ApiModelProperty

/**
 *
 * @author dorjear
 * @Note This JPA model is not a best design. The relation between Customer and Address can be with a better design. Only for short term solution
 * Forget about the DB, this design is good for the new down stream CRMS with Restful call.
 */
@Entity
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated customer ID")
    var id: Int? = null
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the customer")
    var version: Int? = null
    @ApiModelProperty(notes = "The application-specific customer ID")
    var customerId: String? = null
    @ApiModelProperty(notes = "The first name")
    var firstName: String? = null
    @ApiModelProperty(notes = "The last name")
    var lastName: String? = null
    @ApiModelProperty(notes = "The date of birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    var dateOfBirth: Date? = null
    @ApiModelProperty(notes = "The home address")
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    var homeAddress: Address? = null
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @ApiModelProperty(notes = "The postal address")
    var postalAddress: Address? = null
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @ApiModelProperty(notes = "The work address")
    var workAddress: Address? = null

}
