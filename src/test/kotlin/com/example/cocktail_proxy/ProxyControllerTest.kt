package com.example.cocktail_proxy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProxyControllerTest {

    private val cocktailDbApiBaseUrl = "https://www.thecocktaildb.com/api/json/v1/1/"

    @Test
    fun `should compose Cocktail DB URL based on proxy app GET request`() {

        val controller = CocktailDbProxyController()

        val urlWithoutQueryString = controller.composeRequestUrl(baseUrl = cocktailDbApiBaseUrl, path = "random.php")
        val urlWithQueryString = controller.composeRequestUrl(baseUrl = cocktailDbApiBaseUrl, path = "search.php", queryString = "s=Margarita")

        assertEquals(urlWithoutQueryString, "${cocktailDbApiBaseUrl}random.php")
        assertEquals(urlWithQueryString, "${cocktailDbApiBaseUrl}search.php?s=Margarita")
    }
}

// Integration tests are badly needed. But not sure how to go about them (mocking? real requests over internet?).
// Things to check:
// - the correct Cocktail DB URL is composed and called
// - the request type is GET
// - the response status code is 200
// - a JSON object is returned