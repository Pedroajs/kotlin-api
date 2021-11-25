package com.learningkt.bankapi.service

import com.learningkt.bankapi.model.Account
import com.learningkt.bankapi.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.lang.RuntimeException
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRepository) : AccountService {
    override fun create(account: Account): Account {
        Assert.hasLength(account.name,  "[nome] não pode estar em branco!")
        Assert.isTrue(account.name.length >= 5,  "[nome] deve ter no minimo 5 caracteres!")

        Assert.hasLength(account.document,  "[document] não pode estar em branco!")
        Assert.isTrue(account.document.length >= 11, "[document] deve ter no minimo 11 caracteres!")

        Assert.hasLength(account.phone,  "[phone] não pode estar em branco!")
        Assert.isTrue(account.phone.length >= 11, "[phone] deve ter no minimo 5 caracteres!")


        return repository.save(account)
    }

    override fun getAll(): List<Account> {
        return repository.findAll()
    }

    override fun getById(id: Long): Optional<Account> {
        return repository.findById(id)
    }

    override fun upDate(id: Long, account: Account): Optional<Account> {
        val optional = getById(id)
        if(optional.isEmpty) Optional.empty<Account>()

        return optional.map {
            val accountToUpDate = it.copy(
                    name = account.name,
                    document = account.document,
                    phone = account.phone
            )
            repository.save(accountToUpDate)
        }
    }

    override fun delete(id: Long){
        repository.findById(id).map{
            repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElseThrow{throw RuntimeException("Id $id not found")}


    }
}