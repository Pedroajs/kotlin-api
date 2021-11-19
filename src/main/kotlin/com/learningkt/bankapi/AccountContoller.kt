package com.learningkt.bankapi

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")

class AccountContoller(private val repository: AccountRepository) {
    @PostMapping
    fun create(@RequestBody account: Account):Account = repository.save(account)

    @GetMapping
    fun getAll(): List<Account> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Account> =
            repository.findById(id).map{
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())
}