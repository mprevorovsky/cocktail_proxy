package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.cocktailDbApiBaseUrl
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.service.ProxyService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.io.IOException


/*
All GET requests to the app "/proxy/" endpoint are just redirected to the Cocktail DB.
The path and any query strings are extracted and passed on to the Cocktail DB API.

The whole functionality downstream of the Controller is split between a Service layer (ProxyService)
and Repository layer (ProxyDataSource) to allow better testability of the code and provide
modularity for potential future changes of the App logic.
*/


@RestController
@RequestMapping("/proxy/")
class CocktailDbProxyController(
    private val httpRequest: HttpServletRequest,
    private val service: ProxyService
) {

    @ExceptionHandler(HttpClientErrorException::class)
    fun uriNotFound(e: HttpClientErrorException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)


    @ExceptionHandler(IOException::class)
    fun noDataCouldBeReadFromUri(e: IOException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @GetMapping("{path}")
    fun performProxyGetRequest(
        @PathVariable path: String,
    ): CocktailDbRecord {

        return service.proxyGetRequest(cocktailDbApiBaseUrl, path, httpRequest.queryString)
    }
}

