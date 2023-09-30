package com.example.cocktail_proxy.model

/**
 * Data Transfer Object class used for parsing the JSON responses from Svátky API.
 *
 * Note that the API returns many more data fields, but these are ignored by this application.
 */
data class NameDay(
    val name: String
)

