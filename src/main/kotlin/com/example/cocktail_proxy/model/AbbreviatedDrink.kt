package com.example.cocktail_proxy.model

data class AbbreviatedDrinkList(
    val drinks: Collection<AbbreviatedDrink>,
)

data class AbbreviatedDrink(
    val idDrink: Int,
    var strDrink: String,
    var strCategory: String,
    var strAlcoholic: String,
    var strGlass: String,
    var strInstructions: String,
    val strDrinkThumb: String
)