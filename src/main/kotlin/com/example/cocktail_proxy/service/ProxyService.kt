package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DataSource
import com.example.cocktail_proxy.model.CocktailDbRecord
import org.springframework.stereotype.Service

@Service
class ProxyService(
    private val dataSource: DataSource
) {
    fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val response = dataSource.proxyGetRequest(consumedApiBaseUrl, consumedApiPath, queryString)

        // the following steps are just examples of introducing data transformation into the Service layer
        response.drinks?.forEach { it.strDrink = it.strDrink?.uppercase() }
        response.ingredients?.forEach { it.strIngredient = it.strIngredient?.uppercase() }

        return response
    }
}