package com.example.cocktail_proxy

interface ProxyDataSource {

    fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord
}