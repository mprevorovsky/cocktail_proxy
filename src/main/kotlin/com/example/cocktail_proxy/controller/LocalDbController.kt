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
    fun findAllDrinks(): MutableIterable<DrinkJpaCompatible> = drinkRepository.findAll()
}

