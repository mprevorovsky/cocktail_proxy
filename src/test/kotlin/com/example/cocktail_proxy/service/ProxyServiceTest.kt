package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.ProxyDataSource
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ProxyServiceTest {

    private val dataSource: ProxyDataSource = mockk()
    private val proxyService = ProxyService(dataSource)


    @Test
    fun `should call its DataSource once to retrieve cocktail data`() {
        // given
        val baseUrl = ""
        val path = ""
        val queryString = null

        every { dataSource.proxyGetRequest(baseUrl, path, queryString) } returns
                CocktailDbRecord(drinks = null, ingredients = null)

        // when
        proxyService.proxyGetRequest(baseUrl, path, queryString)

        // then
        verify(exactly = 1) { dataSource.proxyGetRequest(baseUrl, path, queryString) }
    }

    @Test
    fun `should return uppercase names of drinks and ingredients`() {
        // given
        val baseUrl = ""
        val path = ""
        val queryString = null

        val mapper = jacksonObjectMapper()
        val mockData = mapper.readValue("""{"drinks":[{"strDrink":"Drink"}],"ingredients":[{"strIngredient":"Vodka"}]}""",
            CocktailDbRecord::class.java)

        every { dataSource.proxyGetRequest(baseUrl, path, queryString) } returns mockData

        // when
        val response = proxyService.proxyGetRequest(baseUrl, path, queryString)

        // then
        assertThat(response.drinks).allMatch { it.strDrink == it.strDrink?.uppercase() }
        assertThat(response.ingredients).allMatch { it.strIngredient == it.strIngredient?.uppercase() }
    }
}