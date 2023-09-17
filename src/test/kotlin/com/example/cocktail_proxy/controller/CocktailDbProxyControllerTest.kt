package com.example.cocktail_proxy.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
class CocktailDbProxyControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return NOT_FOUND http status`() {
        // when/then
        mockMvc.get("/proxy/search.ph")
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return BAD_REQUEST http status`() {
        // when/then
        mockMvc.get("/proxy/search.php") // query string is expected but missing for this remote endpoint
            .andExpect { status { isBadRequest() } }
    }

    @Test
    fun `should return random drink data mapped to CocktailDbRecord`() {
        // when/then
        mockMvc.get("/proxy/random.php")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.drinks.[0].idDrink") { isNumber() }
                jsonPath("$.drinks.[0].strDrink") { isString() }
                jsonPath("$.ingredients") { isEmpty() }
            }
    }

    @Test
    fun `should return ingredient data mapped to CocktailDbRecord`() {
        // when/then
        mockMvc.get("/proxy/search.php?i=vodka")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.drinks") { isEmpty() }
                jsonPath("$.ingredients.[0].idIngredient") { isNumber() }
                jsonPath("$.ingredients.[0].strIngredient") { value("Vodka".uppercase()) }
            }
    }
}