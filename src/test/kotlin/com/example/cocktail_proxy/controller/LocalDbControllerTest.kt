package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.DrinkJpaCompatible
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
class LocalDbControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var drinksRepository: DrinksRepository


    @Test
    @DirtiesContext
    fun `should return drink data from local DB`() {
        // given
        drinksRepository.saveAll(listOf(
            DrinkJpaCompatible(1, "drink1"),
            DrinkJpaCompatible(2, "drink2")))

        // when/then
        mockMvc.get("/local_db/")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.[1].id") { isNotEmpty() }
                jsonPath("$.[1].idDrink") { isNumber() }
                jsonPath("$.[1].strDrink") { isString() }
                jsonPath("$.[2]") { doesNotExist() }
            }
    }
}
