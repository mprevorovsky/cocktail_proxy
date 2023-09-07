package com.example.cocktail_proxy

data class CocktailList(
    val Drinks: Collection<Cocktail>
)

data class Cocktail (
    val idDrink : Int,
    val strDrink : String,
    val strCategory : String,
    val strAlcoholic : String,
    val strGlass : String,
    val strInstructions : String
)
