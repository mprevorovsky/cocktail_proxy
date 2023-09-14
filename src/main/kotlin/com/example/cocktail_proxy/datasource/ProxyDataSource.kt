package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.CocktailDbRecord
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Repository
class ProxyDataSource(val restTemplate: RestTemplate) : DataSource {

    override fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val requestUrl = UriComponentsBuilder
            .fromHttpUrl(consumedApiBaseUrl + consumedApiPath)
            .query(queryString)
            .build()
            .toString()

        return restTemplate.getForObject(requestUrl, CocktailDbRecord::class.java)
            ?: throw IOException("Something went wrong. Did the dog unplug the network cable again?")
    }
}