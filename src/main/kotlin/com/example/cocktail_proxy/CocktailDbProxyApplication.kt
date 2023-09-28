/*
The application is linked to two REST APIs:
===========================================

1) the CocktailDB API (documentation at https://www.thecocktaildb.com/api.php) - a database of drinks.

2) Svátky API (documentation at https://svatkyapi.cz/) - a nameday database.


The application provides 3 endpoints:
=====================================
1) "/proxy" - redirects *all* calls (all paths and queries) to the remote CocktailDB API and return the results
   as JSON data.

   The free public part of the CocktailDB API only provides GET endpoints, which all retrieve information
   on drinks and/or ingredients in some form.
   For simplicity, all JSON content returned from the remote server is mapped onto one common data structure
   (CocktailDbRecord class), and returned by the application.

   Names of drinks and/or ingredients are turned to UPPERCASE as a demonstration of data processing.

   When *new* drink data are retrieved, drink id (idDrink) and name (strDrink) are saved to a local in-memory
   database and can be retrieved from the "/local-db" endpoint.

   All requests, except for the "random.php" path, are cached.

   EXAMPLE USE: /proxy/filter.php?g=Champagne_flute

   NOTE: This single-endpoint solution simplified the implementation of the proxy a lot, but it could become
   limiting if future extensions of the application are required.

2) "/random-drink" - calls the "random.php" path on the CocktailDB API to retrieve data for a random drink,
   and then retrieves the currently celebrated name from Svátky API.

   The results are presented as a simple webpage (Thymeleaf template).

   NOTE: no data are saved to the local in-memory DB when this endpoint is accessed.

3) "/local-db" - retrieves all drink records stored in the local in-memory H2 database.
*/


package com.example.cocktail_proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching


const val cocktailDbApiBaseUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/"
const val cocktailDbApiRandomDrinkUrl: String = "https://www.thecocktaildb.com/api/json/v1/1/random.php"
const val nameDaysApiTodayUrl: String = "https://svatkyapi.cz/api/day"


@SpringBootApplication
@EnableCaching
class CocktailDbProxyApplication


fun main(args: Array<String>) {
    runApplication<CocktailDbProxyApplication>(*args)
}
