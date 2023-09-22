/*
Repository layer for accessing the CocktailDB API.

URI elements supplied by upstream layers are turned into a final URI and a GET request is performed.
 */

package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.CocktailDbRecord
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Repository
class ProxyDataSource(
    private val restTemplate: RestTemplate
) : DataSource {

    // Requests to the "random.php" path are not cached to preserve the random character of the responses.
    @Cacheable("cocktailDb", condition = "#consumedApiPath!='random.php'")
    override fun performProxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val requestUri = buildRequestUri(consumedApiBaseUrl, consumedApiPath, queryString)

        return restTemplate.getForObject(requestUri, CocktailDbRecord::class.java)
            ?: throw IOException("400 Bad Request: No data could be read from $requestUri")
    }


    // Builds URI from base URL, path and an optional query string.
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