package com.example.cocktail_proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


// The application should function as a proxy for the Cocktail DB API (RESTful).
// API documentation can be found at https://www.thecocktaildb.com/api.php.
//
// Data are returned as JSON node objects.


const val cocktailDbApiBaseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/"
const val cocktailDbApiRandomDrinkUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/random.php"


@SpringBootApplication
class CocktailDbProxyApplication


@Configuration
class CocktailDbProxyApplicationConfiguration {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}


fun main(args: Array<String>) {
    runApplication<CocktailDbProxyApplication>(*args)
}
