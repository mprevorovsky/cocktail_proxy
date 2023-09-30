package com.example.cocktail_proxy.utils

import com.example.cocktail_proxy.model.Drink
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate


@SpringBootTest
class GetRandomDrinkFromCocktailDbTest {

    private val restTemplate = RestTemplate()

    @Test
    fun `should retrieve a random drink from CocktailDB`() {
        // given/when
        val randomDrink = getRandomDrinkFromCocktailDb(restTemplate)

        //then
        assertThat(randomDrink).isInstanceOf(Drink::class.java)
        assertThat(randomDrink.idDrink).isNotNull()
        assertThat(randomDrink.strDrink).isNotBlank()
    }
}