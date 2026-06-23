package com.example.spiritguide.model

import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Cocktail>?
)

data class Cocktail(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val imageUrl: String,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strCategory") val category: String?
)