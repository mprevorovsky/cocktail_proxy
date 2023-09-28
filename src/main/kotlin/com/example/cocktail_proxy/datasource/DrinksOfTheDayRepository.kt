/*
Interface defining a CRUD repository.

Intended to manipulate the local in-memory DB with drink-of-the-day data.
 */

package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.DrinkOfTheDay
import org.springframework.data.repository.CrudRepository


interface DrinksOfTheDayRepository : CrudRepository<DrinkOfTheDay, Long> {
    fun findByDate(date: String): DrinkOfTheDay?
}
