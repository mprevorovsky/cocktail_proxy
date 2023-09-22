/*
Controller for the endpoint "/local_db/"

Returns all currently available drink data from the local in-memory DB as JSON.
 */

package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.datasource.DrinksRepository
import com.example.cocktail_proxy.model.DrinkJpaCompatible
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/local_db/")
class LocalDbController(
    private val drinkRepository: DrinksRepository
) {

    @GetMapping
    fun getAllDrinks(): MutableIterable<DrinkJpaCompatible> = drinkRepository.findAll()
}

