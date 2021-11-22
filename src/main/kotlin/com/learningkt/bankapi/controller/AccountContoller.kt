package com.learningkt.bankapi.controller

import com.learningkt.bankapi.model.Account
import com.learningkt.bankapi.repository.AccountRepository
import com.learningkt.bankapi.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")

class AccountContoller(private val service: AccountService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: Account): Account = service.create(account)

    @GetMapping
    fun getAll(): List<Account> = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Account> =
            service.getById(id).map{
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun upDate(@PathVariable id: Long, @RequestBody account: Account): ResponseEntity<Account> =
            service.upDate(id, account).map{
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.OK)

    }
}
