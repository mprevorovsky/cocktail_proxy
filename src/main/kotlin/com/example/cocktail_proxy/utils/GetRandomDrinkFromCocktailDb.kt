package com.example.cocktail_proxy.utils

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import org.springframework.web.client.RestTemplate
import java.io.IOException


/**
 * Retrieves data for a random drink from the CocktailDB.
 */
fun getRandomDrinkFromCocktailDb(restTemplate: RestTemplate): Drink {
    return restTemplate
        .getForEntity(cocktailDbApiRandomDrinkUrl, CocktailDbRecord::class.java)
        .body
        ?.drinks
        ?.first()

        ?: throw IOException("Could not retrieve a random drink from $cocktailDbApiRandomDrinkUrl")
}
