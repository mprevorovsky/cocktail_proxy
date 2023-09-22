/*
Interface for the Repository layer objects.

Intended to relay requests to the CocktailDB API.
 */


package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.CocktailDbRecord

interface DataSource {

    fun performProxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord
}