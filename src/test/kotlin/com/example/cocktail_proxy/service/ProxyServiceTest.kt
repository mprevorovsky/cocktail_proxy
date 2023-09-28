package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.datasource.ProxyDataSource
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest
class ProxyServiceTest() {

    private val dataSource: ProxyDataSource = mockk()
    @Autowired
    lateinit var drinksRepository: DrinksRepository

    @Test
    fun `should call its DataSource once to retrieve cocktail data`() {
        // given
        val proxyService = ProxyService(dataSource = dataSource, drinksLocalRepository = drinksRepository)

        val baseUrl = ""
        val path = ""
        val queryString = null

        every { dataSource.performProxyGetRequest(baseUrl, path, queryString) } returns
                CocktailDbRecord(drinks = null, ingredients = null)

        // when
        proxyService.performProxyGetRequest(baseUrl, path, queryString)

        // then
        verify(exactly = 1) { dataSource.performProxyGetRequest(baseUrl, path, queryString) }
    }


    @Test
    fun `should return uppercase names of drinks and ingredients`() {
        // given
        val proxyService = ProxyService(dataSource = dataSource, drinksLocalRepository = drinksRepository)

        val baseUrl = ""
        val path = ""
        val queryString = null

        val mapper = jacksonObjectMapper()
        val mockData = mapper.readValue("""{"drinks":[{"strDrink":"Drink"}],"ingredients":[{"strIngredient":"Vodka"}]}""",
            CocktailDbRecord::class.java)

        every { dataSource.performProxyGetRequest(baseUrl, path, queryString) } returns mockData

        // when
        val response = proxyService.performProxyGetRequest(baseUrl, path, queryString)

        // then
        assertThat(response.drinks).allMatch { it.strDrink == it.strDrink.uppercase() }
        assertThat(response.ingredients).allMatch { it.strIngredient == it.strIngredient.uppercase() }
    }


    @Test
    @DirtiesContext
    fun `should not save duplicate drinks to DB`() {
        // given
        val proxyService = ProxyService(dataSource = dataSource, drinksLocalRepository = drinksRepository)

        val mapper = jacksonObjectMapper()
        val drink1 = mapper.readValue("""{"idDrink":1,"strDrink":"Drink1"}""", Drink::class.java)
        val drink2 = mapper.readValue("""{"idDrink":2,"strDrink":"Drink2"}""", Drink::class.java)

        // when
        proxyService.saveDrinkDataIfNotExists(listOf(drink1, drink2, drink1))

        // then
        assertThat(drinksRepository.findAll())
            .hasSize(2)
        assertThat(drinksRepository.findAll().first().strDrink)
            .isEqualTo(drink1.strDrink)
        assertThat(drinksRepository.findAll().elementAt(1).strDrink)
            .isEqualTo(drink2.strDrink)
    }
}