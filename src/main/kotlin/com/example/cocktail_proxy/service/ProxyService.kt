package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DataSource
import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import org.springframework.stereotype.Service

/**
 * Service layer for accessing the CocktailDB API.
 *
 * - Relays URI elements supplied by the upstream layers to the Repository layer (DataSource object).
 *
 * - Retrieved drink data are mapped to a DrinkJpaCompatible object and saved to a local in-memory DB
 * (DrinksRepository object).
 * This functionality should likely be made less coupled, e.g. by defining a Service layer interface
 * with a "saveDrinkDataIfNotExists()" function. This would simplify potential future swapping of the
 * current H2 database for some other storage solution.
 *
 * - To demonstrates the possibilities of data transformation in the Service layer, the CocktailDB response
 * data are processed before being returned (the names of any Drinks and/or Ingredients are turned to uppercase)
 */
@Service
class ProxyService(
    private val dataSource: DataSource,
    private val drinksLocalRepository: DrinksRepository
) {
    fun performProxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val response = dataSource.performProxyGetRequest(consumedApiBaseUrl, consumedApiPath, queryString)

        // If any drink data is returned, each *new* drink is saved to an in-memory database.
        if (!response.drinks.isNullOrEmpty()) {
            saveDrinkDataIfNotExists(response.drinks)
        }

        return makeNamesUppercase(response)
    }


    /**
     * Turns drink and ingredient names to uppercase to demonstrate data processing.
     */
    internal fun makeNamesUppercase(responseToProcess: CocktailDbRecord): CocktailDbRecord {
        responseToProcess.drinks?.forEach { it.strDrink = it.strDrink.uppercase() }
        responseToProcess.ingredients?.forEach { it.strIngredient = it.strIngredient.uppercase() }

        return responseToProcess
    }


    /**
     * Saves new drink data to the local in-memory DB.
     */
    internal fun saveDrinkDataIfNotExists(drinkData: Collection<Drink>) {
        drinkData.forEach {
            if (!drinksLocalRepository.existsByIdDrink(it.idDrink))
                drinksLocalRepository.save(it.toDrinkJpaCompatible())
        }
    }
}