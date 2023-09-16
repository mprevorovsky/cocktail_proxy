package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.ProxyDataSource
import com.example.cocktail_proxy.model.CocktailDbRecord
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ProxyServiceTest {
    private val dataSource: ProxyDataSource = mockk()
    private val proxyService = ProxyService(dataSource)


    @Test
    fun `should call its data source once to retrieve cocktail data`() {
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

}