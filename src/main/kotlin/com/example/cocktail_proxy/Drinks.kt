package com.example.cocktail_proxy

data class DrinkList (
    val drinks : Collection<Drink>
)

data class Drink (
    val idDrink : Int,
    val strDrink : String,
    val strCategory : String,
    val strAlcoholic : String,
    val strGlass : String,
    val strInstructions : String
)
