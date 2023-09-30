package com.example.cocktail_proxy.controller

import com.example.cocktail_proxy.model.DrinkJpaCompatible
import com.example.cocktail_proxy.service.DrinkOfTheDayService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.DateTimeException
import java.time.LocalDate

/**
 * Controller for the endpoint "/drink-of-the-day"
 *
 * Returns the drink associated with a supplied date from the local in-memory DB as JSON.
 *
 * If no such record exists yet, a random drink is fetched from www.thecocktaildb.com/api/json/v1/1/random.php,
 * associated with the date, saved to the local in-memory DB and returned as JSON.
 * In case of duplicity (fetched random drink already exists in the local DB, but is not yet associated with
 *
 * any date), the existing drink record is just updated (date property).
 *
 * EXAMPLE USE: "/drink-of-the-day/2023/09/13"
 */
@Controller
@RequestMapping("/drink-of-the-day")
class DrinkOfTheDayController(

    private val drinkOfTheDayService: DrinkOfTheDayService
) {

    /**
     * Handles exceptions triggered by attempting to access invalid date paths.
     */
    @ExceptionHandler(DateTimeException::class)
    fun handleInvalidDatePath(e: DateTimeException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @ResponseBody
    @GetMapping("/{year}/{month}/{day}")
    fun getDrinkOfTheDay(
        @PathVariable year: Int,
        @PathVariable month: Int,
        @PathVariable day: Int
    ): DrinkJpaCompatible {

        // casting to LocalDate is performed for date validation
        val date = LocalDate.of(year, month, day).toString()

        return drinkOfTheDayService.getDrinkOfTheDay(date)
    }
}
