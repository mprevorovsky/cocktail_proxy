package com.example.cocktail_proxy

import com.fasterxml.jackson.databind.JsonNode
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


const val cocktailDbApiBaseUrl = "https://www.thecocktaildb.com/api/json/v1/1/"

@RestController
@RequestMapping("/proxy/")
class CocktailDbProxyController {

    val restTemplate = RestTemplate()

    @GetMapping("{path}")
    @ResponseBody
    fun getRequestProxy(@PathVariable path: String?, request: HttpServletRequest): JsonNode? {

        val queryString : String? = request.queryString

        val requestUrl = if (queryString.isNullOrBlank()) {
            "${cocktailDbApiBaseUrl}${path}"
        } else {
            "${cocktailDbApiBaseUrl}${path}?${queryString}"
        }

        return restTemplate.getForObject(requestUrl, JsonNode::class.java)
    }
}