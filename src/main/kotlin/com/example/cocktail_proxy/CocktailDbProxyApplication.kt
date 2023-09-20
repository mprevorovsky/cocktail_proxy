package com.example.cocktail_proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/*
The application is linked to the CocktailDB REST API.
The CocktailDB API documentation can be found at https://www.thecocktaildb.com/api.php.

The free public part of the CocktailDB API only provides GET endpoints, which all retrieve information
either on drinks or on ingredients is some form.
For simplicity, all JSON content returned from the remote server is mapped onto one common data structure
(CocktailDbRecord class), and returned by the application.

The application provides 3 endpoints:
1) "/proxy/" - redirects *all* calls (all paths and queries) to the remote CocktailDB API and return the results
   as JSON data.
   NOTE: This single-endpoint solution simplified the proxy implementation a lot, but could become limiting if
   future extension of the application are required.
   Names of drinks and/or ingredients are turned to UPPERCASE as a demonstration of data processing.
   When new drink data are retrieved, drink id (idDrink) and name (strDrink) are saved to a local in-memory
   database and can be retrieved from the "/local_db/ endpoint".
2) "/random_drink/" - calls the "random.php" path on the CocktailDB API to retrieve data for a random drink,
   presented as a simple webpage (Thymeleaf template).
   (no data is saved to the local in-memory DB)
3) "/local_db/" - retrieves all drink records stored in the local in-memory DB in the JSON format.
*/

const val cocktailDbApiBaseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/"
const val cocktailDbApiRandomDrinkUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/random.php"


@SpringBootApplication
class CocktailDbProxyApplication


fun main(args: Array<String>) {
    runApplication<CocktailDbProxyApplication>(*args)
}
