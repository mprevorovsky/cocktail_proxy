package com.example.cocktail_proxy.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class RandomDrinkControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DirtiesContext
    fun `should return random drink and name day HTML page`() {
        // when/then
        mockMvc.get("/random-drink")
            .andExpect {
                status { isOk() }
                content { contentType("text/html;charset=utf-8") }
                model { attributeExists(
                    "strDrink",
                    "strInstructions",
                    "nameDayPhrase") }
            }
    }
}