package com.learningkt.bankapi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// Entity informa que uma classe é uma entidade e que seus objetos devem ser persistidos no db
// O JPA obriga que toda classe entity tenha um ID
@Entity(name ="accounts")
data class Account(
        @Id @GeneratedValue
         var id: Long? = null,
         val name: String,
         val document: String,
         val phone: String

)