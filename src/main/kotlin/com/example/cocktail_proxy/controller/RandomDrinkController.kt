package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.service.RandomDrinkService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * Controller for the endpoint "/random-drink"
 *
 * A controller for retrieving brief info on a random drink from
 * www.thecocktaildb.com/api/json/v1/1/random.php
 * and the currently celebrated name from Svátky API.
 * Selected drink attributes and the celebrated name are then served as an HTML page using
 * a Thymeleaf template.
 * Any new drink data are saved to a local in-memory DB.
*/
@Controller
@RequestMapping("/random-drink")
class RandomDrinkController(
    private val randomDrinkService: RandomDrinkService,
) {

    @GetMapping
    fun getRandomDrinkAndCelebratedName(model: Model) = randomDrinkService.getRandomDrinkAndCelebratedName(model)
}
