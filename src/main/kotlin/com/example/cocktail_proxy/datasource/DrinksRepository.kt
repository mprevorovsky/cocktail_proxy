package com.example.cocktail_proxy.datasource

import com.example.cocktail_proxy.model.DrinkJpaCompatible
import org.springframework.data.repository.CrudRepository


interface DrinksRepository: CrudRepository<DrinkJpaCompatible, Long>
{
    fun existsByIdDrink(idDrink: Int): Boolean
}