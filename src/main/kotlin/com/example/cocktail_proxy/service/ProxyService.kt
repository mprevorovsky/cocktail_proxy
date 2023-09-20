package com.example.cocktail_proxy.service

import com.example.cocktail_proxy.datasource.DataSource
import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.CocktailDbRecord
import com.example.cocktail_proxy.model.Drink
import org.springframework.stereotype.Service

@Service
class ProxyService(
    private val dataSource: DataSource,
    private val drinkRepository: DrinksRepository
) {
    fun proxyGetRequest(
        consumedApiBaseUrl: String,
        consumedApiPath: String,
        queryString: String?
    ): CocktailDbRecord {

        val response = dataSource.proxyGetRequest(consumedApiBaseUrl, consumedApiPath, queryString)

        // if any drink data is returned, each *new* drink is saved to an in-memory database
        if (!response.drinks.isNullOrEmpty()) {
            saveDrinkData(response.drinks)
        }

        // to demonstrates the possibilities of data transformation in the Service layer
        // the CocktailDB response data are processed before being returned
        // (the names of any Drinks and/or Ingredients are turned to uppercase)
        return makeNamesUppercase(response)
    }


    fun makeNamesUppercase(responseToProcess: CocktailDbRecord): CocktailDbRecord {
        responseToProcess.drinks?.forEach { it.strDrink = it.strDrink.uppercase() }
        responseToProcess.ingredients?.forEach { it.strIngredient = it.strIngredient.uppercase() }

        return responseToProcess
    }


    fun saveDrinkData(drinkData: Collection<Drink>) {
        drinkData.forEach {
            if (!drinkRepository.existsByIdDrink(it.idDrink))
                drinkRepository.save(it.toDrinkJpaCompatible())
        }
    }
}