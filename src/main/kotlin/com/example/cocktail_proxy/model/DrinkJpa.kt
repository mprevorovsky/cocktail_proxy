package com.example.cocktail_proxy.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id


// default values for idDrink and strDrink are provided to force Kotlin to
// create a default constructor so that it does not clash with Jakarta Persistence API
@Entity
class DrinkJpaCompatible (
    var idDrink: Int = -1,
    var strDrink: String = "",
    @Id @GeneratedValue var id: Long? = null
)
