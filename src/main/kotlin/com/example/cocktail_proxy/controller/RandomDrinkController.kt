package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.io.IOException

@RestController
@RequestMapping("/random_drink/")
class RandomDrinkController(
    val consumedApiUrl: String = cocktailDbApiRandomDrinkUrl,
    val restTemplate: RestTemplate
) {

    @GetMapping
    fun getRandomDrink(): Drink {

        val response = restTemplate.getForEntity(consumedApiUrl, CocktailDbRecord::class.java)
        return response.body?.drinks?.first()
            ?: throw IOException("Could not fetch any drinks. Have a snack instead...")
    }
}


