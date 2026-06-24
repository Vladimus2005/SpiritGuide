package com.example.spiritguide.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spiritguide.db.CocktailDao
import com.example.spiritguide.model.Cocktail
import com.example.spiritguide.network.RetrofitInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CocktailViewModel(private val dao: CocktailDao) : ViewModel(){
    var cocktails by mutableStateOf<List<Cocktail>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dao.getAllCocktails().collect { localCocktails ->
                cocktails = localCocktails
                if (localCocktails.isNotEmpty()) {
                    errorMessage = null
                }
            }
        }
        fetchCocktails()
    }

    private fun fetchCocktails() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = RetrofitInstance.api.getAlcoholicCocktails()
                response.drinks?.let { dao.insertCocktails(it) }
            } catch (e: Exception) {
                delay(500)

                if (cocktails.isEmpty()) {
                    errorMessage = "Nu s-au putut încărca datele. Verifică conexiunea la internet!"
                }
            } finally {
                isLoading = false
            }
        }
    }
    fun loadCocktailDetails(cocktail: Cocktail) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCocktailDetails(cocktail.id)
                response.drinks?.let { detailedCocktails ->
                    dao.insertCocktails(detailedCocktails)
                }
            } catch (e: Exception) {
                val errorCocktail = cocktail.copy(
                    instructions = "Eroare: Nu s-a putut descărca rețeta. Verifică conexiunea la internet!"
                )
                dao.insertCocktails(listOf(errorCocktail))
            }
        }
    }
}

class CocktailViewModelFactory(private val dao: CocktailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CocktailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}