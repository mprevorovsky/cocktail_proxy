package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.Drink
import com.example.cocktail_proxy.model.NameDay
import com.example.cocktail_proxy.nameDaysApiTodayUrl
import com.example.cocktail_proxy.utils.getRandomDrinkFromCocktailDb
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.client.RestTemplate
import java.io.IOException

/**
 * Service layer for retrieving required data for the "/random-drink" endpoint.
 *
 * Retrieves 1 random drink from the CocktailDB.
 * Retrieves the currently celebrated name from Svátky API.
 *
 * Retrieved drink data are mapped to a DrinkJpaCompatible object and saved to a local in-memory DB
 * (DrinksRepository object) if not already present.
 */
@Service
class RandomDrinkService(
    private val restTemplate: RestTemplate,
    private val drinksLocalRepository: DrinksRepository
) {

    fun getRandomDrinkAndCelebratedName(model: Model) {
        // get data for 1 random drink (from thecocktaildb.com)
        val randomDrink = getRandomDrinkFromCocktailDb(restTemplate)

        // get the name celebrated today (from svatkyapi.cz)
        val nameCelebratedToday = getNameCelebratedToday()

        // pass obtained data to a HTML template
        model.addAttribute("strDrink", randomDrink.strDrink)
        model.addAttribute("strInstructions", randomDrink.strInstructions)
        model.addAttribute("strDrinkThumb", randomDrink.strDrinkThumb)
        model.addAttribute("nameDayPhrase", "$nameCelebratedToday celebrates today... Cheers!")

        saveDrinkDataIfNotExists(randomDrink)
    }

    /**
     * Retrieves the currently celebrated name from Svátky API.
     */
    private fun getNameCelebratedToday(): String {
        return restTemplate
            .getForEntity(nameDaysApiTodayUrl, NameDay::class.java)
            .body
            ?.name

            ?: throw IOException("Could not retrieve a random drink from $nameDaysApiTodayUrl")
    }

    /**
     * Saves new drink data to the local in-memory DB.
     */
    internal fun saveDrinkDataIfNotExists(drink: Drink) {
        if (!drinksLocalRepository.existsByIdDrink(drink.idDrink))
            drinksLocalRepository.save(drink.toDrinkJpaCompatible())
    }
}