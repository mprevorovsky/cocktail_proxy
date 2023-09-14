package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DataSource
import com.example.cocktail_proxy.model.CocktailDbRecord
import org.springframework.stereotype.Service

@Service
class ProxyService(
    val dataSource: DataSource
) {
    fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        return dataSource.proxyGetRequest(consumedApiBaseUrl, consumedApiPath, queryString)
    }
}