/*
Interface defining a CRUD repository.

Intended to manipulate the local in-memory DB with drink data.
 */

package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.DrinkJpaCompatible
import org.springframework.data.repository.CrudRepository


interface DrinksRepository: CrudRepository<DrinkJpaCompatible, Long>
{
    fun existsByIdDrink(idDrink: Int): Boolean

    fun findByDate(date: String): DrinkJpaCompatible?

    fun findByIdDrink(idDrink: Int): DrinkJpaCompatible?
}
