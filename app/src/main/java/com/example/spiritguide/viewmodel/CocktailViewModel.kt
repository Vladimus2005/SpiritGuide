package com.example.spiritguide.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spiritguide.model.Cocktail
import com.example.spiritguide.network.RetrofitInstance
import kotlinx.coroutines.launch

class CocktailViewModel : ViewModel(){
    var cocktails by mutableStateOf<List<Cocktail>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        fetchCocktails()
    }

    private fun fetchCocktails() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = RetrofitInstance.api.getAlcoholicCocktails()
                cocktails = response.drinks ?: emptyList()
            } catch (e: Exception) {
                errorMessage = "Nu s-au putut încărca datele. Verifică conexiunea la internet!"
            } finally {
                isLoading = false
            }
        }
    }
}