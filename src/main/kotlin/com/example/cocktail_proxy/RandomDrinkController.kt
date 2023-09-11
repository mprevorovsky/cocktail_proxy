package com.example.cocktail_proxy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.io.IOException

@RestController
@RequestMapping("/random_drink/")
class RandomDrinkController {

    private val restTemplate = RestTemplate()

    @GetMapping
    fun getRandomDrink() : Collection<Drink> {

        val response =  restTemplate.getForEntity(cocktailDbApiRandomDrinkUrl, DrinkList::class.java)
        return response.body?.drinks
            ?: throw IOException("Could not fetch any drinks.")
    }
}


