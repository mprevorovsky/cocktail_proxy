package com.example.cocktail_proxy.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class DrinkOfTheDayControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DirtiesContext
    fun `should return drink of the day`() {
        // given
        val year = "2023"
        val month = "12"
        val day = "12"
        val date = "$year-$month-$day"

        // when/then
        mockMvc.get("/drink-of-the-day/$year/$month/$day")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.idDrink") { isNumber() }
                jsonPath("$.strDrink") { isString() }
            }

        mockMvc.get("/local-db")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.[0].date") { value(date) }
            }
    }
}