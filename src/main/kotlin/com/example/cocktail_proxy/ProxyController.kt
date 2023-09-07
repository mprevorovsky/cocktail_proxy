package com.example.cocktail_proxy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


val serverApiUrl = "https://www.thecocktaildb.com/api/json/v1/1"

@RestController
@RequestMapping
class CocktailProxy {

    val restTemplate = RestTemplate()
    @GetMapping("/random.php")
    fun getProxy(): String? {
        return restTemplate.getForObject("${serverApiUrl}/random.php", String::class.java)
    }
}