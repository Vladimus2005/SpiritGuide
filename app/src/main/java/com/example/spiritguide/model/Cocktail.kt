package com.example.spiritguide.model

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Cocktail>?
)

@Entity(tableName = "cocktails")
data class Cocktail(
    @PrimaryKey
    @SerializedName("idDrink") val id: String,

    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val imageUrl: String,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strCategory") val category: String?
)