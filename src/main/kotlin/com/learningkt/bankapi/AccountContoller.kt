package com.learningkt.bankapi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")

class AccountContoller(private val repository: AccountRepository) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: Account):Account = repository.save(account)

    @GetMapping
    fun getAll(): List<Account> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Account> =
            repository.findById(id).map{
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun upDate(@PathVariable id: Long, @RequestBody account: Account): ResponseEntity<Account> =
            repository.findById(id).map{
                val accountUpDate = it.copy(
                        name = account.name,
                        document = account.document,
                        phone = account.phone
                )
                ResponseEntity.ok(repository.save(accountUpDate))
            }.orElse(ResponseEntity.notFound().build())
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
            repository.findById(id).map{
                repository.delete(it)
                ResponseEntity<Void>(HttpStatus.OK)
            }.orElse(ResponseEntity.notFound().build())
}