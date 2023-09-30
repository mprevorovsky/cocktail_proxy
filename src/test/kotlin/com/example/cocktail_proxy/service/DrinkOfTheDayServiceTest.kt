package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.DrinkJpaCompatible
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.client.RestTemplate

@SpringBootTest
class DrinkOfTheDayServiceTest {

    @Autowired
    lateinit var drinksRepository: DrinksRepository
    private val restTemplate = RestTemplate()

    @Test
    @DirtiesContext
    fun `should retrieve a new random drink and save it to local DB`() {
        // given
        val drinksService = DrinkOfTheDayService(drinksRepository, restTemplate)
        val date = "2023-12-12"

        // when
        val drink = drinksService.getDrinkOfTheDay(date)

        // then
        assertThat(drink).isNotNull
        assertThat(drink.date).isEqualTo(date)

        assertThat(drinksRepository.findAll()).hasSize(1)
        assertThat(drinksRepository.findByDate(date)).isNotNull
    }

    @Test
    @DirtiesContext
    fun `should retrieve existing date-associated drink from local DB`() {
        // given
        val drinksService = DrinkOfTheDayService(drinksRepository, restTemplate)
        val drink = DrinkJpaCompatible(
            idDrink = 10,
            strDrink = "Drink",
            date = "2023-12-12"
        )
        drinksRepository.save(drink)

        // when
        val retrievedDrink = drinksService.getDrinkOfTheDay(drink.date!!)

        // then
        assertThat(retrievedDrink.strDrink).isEqualTo(drink.strDrink)
        assertThat(retrievedDrink.id).isEqualTo(1)
        assertThat(drinksRepository.findAll()).hasSize(1)
    }

    @Test
    @DirtiesContext
    fun `should update instead of duplicating when random drink already exists in local DB`() {
        // given
        val restTemplate: RestTemplate = mockk()
        val drinksService = DrinkOfTheDayService(drinksRepository, restTemplate)

        val drink = DrinkJpaCompatible(
            idDrink = 10,
            strDrink = "Drink",
            date = null
        )
        drinksRepository.save(drink)

        val date = "2023-12-12"

        val mapper = jacksonObjectMapper()
        val randomDrink = mapper.readValue("""{"drinks":[{"idDrink":10,"strDrink":"Drink"}]}""", CocktailDbRecord::class.java)
        every { restTemplate
            .getForEntity(cocktailDbApiRandomDrinkUrl, CocktailDbRecord::class.java)
            .body
        } returns randomDrink

        // when
        val retrievedDrink = drinksService.getDrinkOfTheDay(date)

        // then
        assertThat(drinksRepository.findAll()).hasSize(1)
        assertThat(retrievedDrink.strDrink).isEqualTo(drink.strDrink)
        assertThat(retrievedDrink.date).isEqualTo(date)
    }
}