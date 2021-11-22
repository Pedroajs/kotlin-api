package com.learningkt.bankapi.repository

import com.learningkt.bankapi.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {
}