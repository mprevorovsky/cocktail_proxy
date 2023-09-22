/*
Data Transfer Object classes used for parsing the JSON responses from Svátky API.
 */

package com.example.cocktail_proxy.model

/*
Intended to contain the currently celebrated name.

Note that the API returns many more data fields, but these are ignored by this application.
 */
data class Nameday(
    val name: String
)

