package com.learningkt.bankapi

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")

class AccountContoller(private val repository: AccountRepository) {

}