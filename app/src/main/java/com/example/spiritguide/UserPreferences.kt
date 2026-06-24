package com.example.spiritguide

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class UserPreferences(private val context: Context) {

    companion object {
        val HIDE_DISCLAIMER_KEY = booleanPreferencesKey("hide_age_disclaimer")
    }

    val hideDisclaimerFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[HIDE_DISCLAIMER_KEY] ?: false
        }

    suspend fun setDisclaimerHidden() {
        context.dataStore.edit { preferences ->
            preferences[HIDE_DISCLAIMER_KEY] = true
        }
    }
}