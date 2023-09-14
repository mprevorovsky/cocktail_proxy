package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.cocktailDbApiBaseUrl
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.service.ProxyService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


/*
All GET requests to the app "/proxy/" endpoint are just redirected to the Cocktail DB.
The path and any query strings are extracted and passed on to the Cocktail DB API.
*/


@RestController
@RequestMapping("/proxy/")
class CocktailDbProxyController(
    val restTemplate: RestTemplate,
    val httpRequest: HttpServletRequest,
    val service: ProxyService
) {

    @GetMapping("{path}")
    fun performProxyGetRequest(
        @PathVariable path: String,
    ): CocktailDbRecord {

        return service.proxyGetRequest(cocktailDbApiBaseUrl, path, httpRequest.queryString)
    }
}

