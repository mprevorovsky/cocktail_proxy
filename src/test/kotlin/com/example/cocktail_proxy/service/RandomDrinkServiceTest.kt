package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.DrinkJpaCompatible
import com.example.cocktail_proxy.model.NameDay
import com.example.cocktail_proxy.nameDaysApiTodayUrl
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.ui.ExtendedModelMap
import org.springframework.web.client.RestTemplate

@SpringBootTest
class RandomDrinkServiceTest {

    @Autowired
    private lateinit var drinksRepository: DrinksRepository

    // must not be named "restTemplate" to avoid conflict with mocking the restTemplate further below
    private val testRestTemplate = RestTemplate()

    @Test
    fun `should return some celebrated name`() {
        // given
        val drinksService = RandomDrinkService(testRestTemplate, drinksRepository)

        // when
        val name = drinksService.getNameCelebratedToday()

        // then
        assertThat(name).isInstanceOf(String::class.java)
        assertThat(name).isNotBlank()
    }

    @Test
    @DirtiesContext
    fun `should add celebrated name as attribute to the Model`() {
        // given
        val mockName = NameDay("Bob")
        val mockNameDayPhrase = "${mockName.name} celebrates today... Cheers!"

        val mapper = jacksonObjectMapper()
        val mockCocktailDbRecord = mapper.readValue("""{"drinks":[{"strDrink":"Drink"}],"ingredients":[{"strIngredient":"Vodka"}]}""",
            CocktailDbRecord::class.java)

        val restTemplate: RestTemplate = mockk()

        every { restTemplate
            .getForEntity(nameDaysApiTodayUrl, NameDay::class.java)
            .body
        } returns mockName

        every { restTemplate
            .getForEntity(cocktailDbApiRandomDrinkUrl, CocktailDbRecord::class.java)
            .body
        } returns mockCocktailDbRecord

        val drinksService = RandomDrinkService(restTemplate, drinksRepository)

        val model = ExtendedModelMap()

        // when
        drinksService.getRandomDrinkAndCelebratedName(model)

        // then
        assertThat(model.getAttribute("nameDayPhrase")).isEqualTo(mockNameDayPhrase)
    }

    @Test
    @DirtiesContext
    fun `should not save drink if already exists in local DB`() {
        // given
        val drinksService = RandomDrinkService(testRestTemplate, drinksRepository)
        val drink = DrinkJpaCompatible(
            idDrink = 10,
            strDrink = "Drink",
            date = "2023-12-12"
        )
        drinksRepository.save(drink)

        // when
        drinksService.saveDrinkDataIfNotExists(drink)

        // then
        assertThat(drinksRepository.findAll()).hasSize(1)
        assertThat(drinksRepository.existsByIdDrink(drink.idDrink))
    }
}