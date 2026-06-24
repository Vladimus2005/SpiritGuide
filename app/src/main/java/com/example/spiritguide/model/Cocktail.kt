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
    @SerializedName("strCategory") val category: String?,

    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,

    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?
)

{
    fun getIngredientsList(): List<String> {
        val list = mutableListOf<String>()
        if (!ingredient1.isNullOrBlank()) list.add("${measure1 ?: ""} $ingredient1".trim())
        if (!ingredient2.isNullOrBlank()) list.add("${measure2 ?: ""} $ingredient2".trim())
        if (!ingredient3.isNullOrBlank()) list.add("${measure3 ?: ""} $ingredient3".trim())
        if (!ingredient4.isNullOrBlank()) list.add("${measure4 ?: ""} $ingredient4".trim())
        if (!ingredient5.isNullOrBlank()) list.add("${measure5 ?: ""} $ingredient5".trim())
        if (!ingredient6.isNullOrBlank()) list.add("${measure6 ?: ""} $ingredient6".trim())
        return list
    }
}