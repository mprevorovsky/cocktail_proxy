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

/**
 * Controller for the endpoint "/proxy"
 *
 * All GET requests to this endpoint are just redirected to the CocktailDB.
 * The path and any query strings are extracted and passed on to the CocktailDB API.
 *
 * The whole functionality downstream of the controller is split between:
 * - Service layer (ProxyService class)
 * - Repository layer (ProxyDataSource class)
 * to allow for better testability of the code and to provide modularity for potential
 * future changes of the application logic.
 *
 * Also, server-side HTTP errors are handled by this controller.
 *
 * EXAMPLE USE: /proxy/filter.php?g=Champagne_flute
*/
@RestController
@RequestMapping("/proxy")
class CocktailDbProxyController(
    private val httpRequest: HttpServletRequest,
    private val service: ProxyService
) {

    /**
     * Handles exceptions triggered by requests to non-existing paths at the CocktailDB.
     */
    @ExceptionHandler(HttpClientErrorException::class)
    fun handleUriNotFound(e: HttpClientErrorException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)


    /**
     * Handles exceptions triggered by requests to the CocktailDB with malformed URI
     * (typically the query string).
     */
    @ExceptionHandler(IOException::class)
    fun handleNoDataCouldBeReadFromUri(e: IOException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @GetMapping("{path}")
    fun performProxyGetRequest(
        @PathVariable path: String,
    ): CocktailDbRecord {

        return service.performProxyGetRequest(cocktailDbApiBaseUrl, path, httpRequest.queryString)
    }
}

