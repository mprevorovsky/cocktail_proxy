/*
Controller for the endpoint "/drink-of-the-day"

Returns the drink associated with a supplied date from the local in-memory DB as JSON.

If no such record exists yet, a random drink is fetched from www.thecocktaildb.com/api/json/v1/1/random.php,
associated with the date, saved to the local in-memory DB and returned as JSON.

EXAMPLE USE: "/drink-of-the-day/2023/09/13"
 */

package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.cocktailDbApiRandomDrinkUrl
import com.example.cocktail_proxy.datasource.DrinksOfTheDayRepository
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import com.example.cocktail_proxy.model.DrinkOfTheDay
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.time.LocalDate


@Controller
@RequestMapping("/drink-of-the-day")
class DrinkOfTheDayController(

    private val restTemplate: RestTemplate,
    private val drinksOfTheDayRepository: DrinksOfTheDayRepository
) {

    @ResponseBody
    @GetMapping("/{year}/{month}/{day}")
    fun getDrinkOfTheDay(
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): DrinkOfTheDay {

        val date = LocalDate.of(year, month, day).toString()
        var drinkOfTheDay = drinksOfTheDayRepository.findByDate(date)

        return if (drinkOfTheDay != null) drinkOfTheDay
        else {
            val randomDrink = getRandomDrink()
            drinkOfTheDay = DrinkOfTheDay(
                date = date,
                idDrink = randomDrink.idDrink,
                strDrink = randomDrink.strDrink
            )

            // save new drink-of-the-day data to the local in-memory DB.
            drinksOfTheDayRepository.save(drinkOfTheDay)

            drinkOfTheDay
        }
    }

    // retrieves data for 1 random drink (from thecocktaildb.com)
    internal fun getRandomDrink(): Drink {
        return restTemplate
            .getForEntity(cocktailDbApiRandomDrinkUrl, CocktailDbRecord::class.java)
            .body
            ?.drinks
            ?.first()

            ?: throw IOException("Could not retrieve a random drink from $cocktailDbApiRandomDrinkUrl")
    }
}
