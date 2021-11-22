package com.learningkt.bankapi.service

import com.learningkt.bankapi.model.Account
import org.springframework.http.ResponseEntity

import java.util.*

interface AccountService {
    fun create(account: Account): Account

    fun getAll(): List<Account>

    fun getById(id: Long): Optional<Account>

    fun upDate(id: Long, account: Account): Optional<Account>

    fun delete(id: Long)
}