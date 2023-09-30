package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.DrinkJpaCompatible
import org.springframework.data.repository.CrudRepository

/**
 * Interface defining a CRUD repository.
 *
 * Intended to manipulate the local in-memory DB with drink data.
 */
interface DrinksRepository: CrudRepository<DrinkJpaCompatible, Long>
{

    // Function is auto-implemented by Spring.
    fun existsByIdDrink(idDrink: Int): Boolean

    // Function is auto-implemented by Spring.
    fun findByDate(date: String): DrinkJpaCompatible?

    // Function is auto-implemented by Spring.
    fun findByIdDrink(idDrink: Int): DrinkJpaCompatible?
}
