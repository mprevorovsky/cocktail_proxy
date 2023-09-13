package com.example.cocktail_proxy

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CocktailDbRecord (
    val drinks: Collection<Drink>?,
    val ingredients: Collection<Ingredient>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Drink (
    val idDrink: Int,
    val strDrink: String?,
    val strDrinkAlternate: String?,
    val strTags: String?,
    val strVideo: String?,
    val strCategory: String?,
    @JsonProperty("strIBA")
    val strIba: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strInstructions: String?,
    @JsonProperty("strInstructionsES")
    val strInstructionsEs: String?,
    @JsonProperty("strInstructionsDE")
    val strInstructionsDe: String?,
    @JsonProperty("strInstructionsFR")
    val strInstructionsFr: String?,
    @JsonProperty("strInstructionsIT")
    val strInstructionsIt: String?,
    @JsonProperty("strInstructionsZH-HANS")
    val strInstructionsZhHans: String?,
    @JsonProperty("strInstructionsZH-HANT")
    val strInstructionsZhHant: String?,
    val strDrinkThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strImageSource: String?,
    val strImageAttribution: String?,
    val strCreativeCommonsConfirmed: String?,
    val dateModified: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ingredient (
    val idIngredient: Int,
    val strIngredient: String?,
    val strDescription: String?,
    val strType: String?,
    val strAlcohol: String?,
    @JsonProperty("strABV")
    val strAbv: String?
)