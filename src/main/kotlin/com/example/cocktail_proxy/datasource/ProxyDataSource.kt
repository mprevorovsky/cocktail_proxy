package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.CocktailDbRecord
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Repository
class ProxyDataSource(
    private val restTemplate: RestTemplate
) : DataSource {

    override fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val requestUri = buildRequestUri(consumedApiBaseUrl, consumedApiPath, queryString)

        return restTemplate.getForObject(requestUri, CocktailDbRecord::class.java)
            ?: throw IOException("Something went wrong. Did the dog unplug the network cable again?")
    }


    internal fun buildRequestUri(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): String {

        return UriComponentsBuilder
            .fromHttpUrl(consumedApiBaseUrl + consumedApiPath)
            .query(queryString)
            .build()
            .toString()
    }
}