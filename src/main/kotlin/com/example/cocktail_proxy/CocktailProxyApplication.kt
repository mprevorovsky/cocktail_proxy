package com.example.cocktail_proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


// The application should function as a proxy for the Cocktail DB API (RESTful).
// API documentation can be found at https://www.thecocktaildb.com/api.php.
//
// Data are returned as JSON node objects.


@SpringBootApplication
class CocktailProxyApplication

fun main(args: Array<String>) {
	runApplication<CocktailProxyApplication>(*args)

}
