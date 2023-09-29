/*
Data Transfer Object classes used for interaction with the Jakarta Persistence API.
 */

package com.example.cocktail_proxy.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

/*
Intended to extract a subset of Drink-related attributes from CocktailDBRecord and make
the data object suitable for interaction with the Jakarta Persistence API.

The optional date property is used by the "drink-of-the-day" endpoint to associate the
particular drink with a particular date.

Default values for idDrink and strDrink are provided to force Kotlin to
create a default constructor, so that there is no clash with Jakarta Persistence API.
 */
@Entity
class DrinkJpaCompatible(
    var idDrink: Int = -1,
    var strDrink: String = "",
    var date: String? = null,
    @Id @GeneratedValue var id: Long? = null
)
