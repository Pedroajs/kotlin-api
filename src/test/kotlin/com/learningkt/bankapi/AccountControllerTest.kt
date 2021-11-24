package com.learningkt.bankapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.learningkt.bankapi.model.Account
import com.learningkt.bankapi.repository.AccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var accountRepository: AccountRepository


    @Test
    fun testFindAll(){
        accountRepository.save(Account(name = "Test", document = "123", phone = "98764310" ))
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].document").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun testFindById(){
        val account = accountRepository.save(Account(name = "Test", document = "123", phone = "98764310" ))
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/${account.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun testCreateAccount(){
        val account = accountRepository.save(Account(name = "Test", document = "123", phone = "98764310" ))
        val json = ObjectMapper().writeValueAsString(account)

        accountRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(accountRepository.findAll().isEmpty())
    }

    @Test
    fun testUpDateAccount(){
        val account = accountRepository
                .save(Account(name = "Test", document = "123", phone = "98764310" ))
                .copy(name = "updated")
        val json = ObjectMapper().writeValueAsString(account)

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/${account.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(account.name, findById.get().name)
    }

    @Test
    fun testDeleteAccount(){
        val account = accountRepository
                .save(Account(name = "Test", document = "123", phone = "98764310" ))
                .copy(name = "updated")

        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/${account.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    @Test
    fun `test create account validation error empty name`(){
        val account = accountRepository.save(Account(name = "", document = "123", phone = "98764310" ))
        val json = ObjectMapper().writeValueAsString(account)

        accountRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(!account.name.isEmpty()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andDo(MockMvcResultHandlers.print())
    }
}