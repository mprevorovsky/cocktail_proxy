package com.example.cocktail_proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


// The application should function as a proxy for the Cocktail DB API (RESTful).
// API documentation can be found at https://www.thecocktaildb.com/api.php.
//
// Data are returned as JSON node objects.


const val cocktailDbApiBaseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/"
const val cocktailDbApiRandomDrinkUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/random.php"

@SpringBootApplication
class CocktailDbProxyApplication

fun main(args: Array<String>) {
	runApplication<CocktailDbProxyApplication>(*args)

}
