package com.example.cocktail_proxy

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.io.IOException


/*
All GET requests to the app "/proxy/" endpoint are just redirected to the Cocktail DB.
The path and any query strings are extracted and passed on to the Cocktail DB API.
*/


@RestController
@RequestMapping("/proxy/")
class CocktailDbProxyController(
    val consumedApiBaseUrl: String = cocktailDbApiBaseUrl,
    val restTemplate: RestTemplate = RestTemplate()
    ) {

    @GetMapping("{path}")
    fun performProxyGetRequest(@PathVariable path: String, request: HttpServletRequest): CocktailDbRecord {

        val requestUrl = composeRequestUrl(consumedApiBaseUrl, path, request.queryString)

        return restTemplate.getForObject(requestUrl, CocktailDbRecord::class.java)
            ?: throw IOException("Something went wrong. Did the dog unplug the network cable again?")
    }

    internal fun composeRequestUrl(baseUrl: String, path: String, queryString: String? = null): String {
        return if (!queryString.isNullOrBlank()) {
            "${baseUrl}${path}?${queryString}"
        } else {
            "${baseUrl}${path}"
        }
    }
}

