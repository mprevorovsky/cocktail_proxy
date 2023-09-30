package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.DrinkJpaCompatible
import com.example.cocktail_proxy.utils.getRandomDrinkFromCocktailDb
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


/**
 * Service layer for the drink-of-the-day functionality.
 *
 * Returns the drink associated with a supplied date from the local in-memory DB as JSON
 * (as a DrinkJpaCompatible object).
 *
 * If no such record exists yet, a random drink is fetched from www.thecocktaildb.com/api/json/v1/1/random.php,
 * mapped on a DrinkJpaCompatible object, associated with the date, saved to the local in-memory DB and returned as JSON.
 * In case of duplicity (fetched random drink already exists in the local DB, but is not yet associated with
 * any date), the existing drink record is just updated (date property).
 */
@Service
class DrinkOfTheDayService(

    private val drinksLocalRepository: DrinksRepository,
    private val restTemplate: RestTemplate
) {
    fun getDrinkOfTheDay(
        date: String
    ): DrinkJpaCompatible {

        var drinkOfTheDay = drinksLocalRepository.findByDate(date)

        return if (drinkOfTheDay != null) drinkOfTheDay
        else {
            val randomDrink = getRandomDrinkFromCocktailDb(restTemplate)
            drinkOfTheDay = DrinkJpaCompatible(
                date = date,
                idDrink = randomDrink.idDrink,
                strDrink = randomDrink.strDrink
            )

            // If fetched random drink is already present in the local in-memory DB, the existing
            // drink record is just updated (associated with the date).
            // (idDrink is a unique drink ID used by the CocktailDB)
            if (drinksLocalRepository.existsByIdDrink(drinkOfTheDay.idDrink))
                drinkOfTheDay.id = drinksLocalRepository.findByIdDrink(drinkOfTheDay.idDrink)?.id

            // Save/update drink-of-the-day data to the local in-memory DB.
            drinksLocalRepository.save(drinkOfTheDay)

            drinkOfTheDay
        }
    }
}
