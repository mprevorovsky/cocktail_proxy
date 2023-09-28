/*
Controller for the endpoint "/random-drink"

A mini all-in-one controller for retrieving brief info on a random drink from
www.thecocktaildb.com/api/json/v1/1/random.php
and serving selected drink attributes as an HTML page using a Thymeleaf template.

For simplicity, this endpoint does not implement any Service or Repository layers,
but yes, now the controller does too many different things...
Also, no automated tests are currently available for this controller
(but has been tested manually).
*/


package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import com.example.cocktail_proxy.model.Nameday
import com.example.cocktail_proxy.nameDaysApiTodayUrl
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.RestTemplate
import java.io.IOException


@Controller
@RequestMapping("/random-drink")
class RandomDrinkController(
    private val restTemplate: RestTemplate,
) {

    @GetMapping
    fun getRandomDrinkInfo(model: Model): Drink {

        // get data for 1 random drink (from thecocktaildb.com)
        val randomDrink = restTemplate
            .getForEntity(cocktailDbApiRandomDrinkUrl, CocktailDbRecord::class.java)
            .body
            ?.drinks
            ?.first()

        // get the name celebrated today (from svatkyapi.cz)
        val nameCelebratedToday = restTemplate
            .getForEntity(nameDaysApiTodayUrl, Nameday::class.java)
            .body
            ?.name

        // pass obtained data to a HTML template
        model.addAttribute("strDrink", randomDrink?.strDrink)
        model.addAttribute("strInstructions", randomDrink?.strInstructions)
        model.addAttribute("strDrinkThumb", randomDrink?.strDrinkThumb)
        model.addAttribute("namedayPhrase", nameCelebratedToday + " celebrates today... Cheers!")

        return randomDrink
            ?: throw IOException("Could not retrieve a random drink from $cocktailDbApiRandomDrinkUrl")
    }
}



