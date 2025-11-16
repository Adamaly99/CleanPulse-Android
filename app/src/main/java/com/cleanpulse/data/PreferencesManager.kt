package com.cleanpulse.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.cleanpulse.Constants

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cleanpulse_prefs")

/**
 * Manages user preferences using DataStore
 */
class PreferencesManager(private val context: Context) {
    
    private val dataStore = context.dataStore
    
    // User Preferences
    val userId: Flow<String> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(Constants.PREF_USER_ID)] ?: ""
    }
    
    val userEmail: Flow<String> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(Constants.PREF_USER_EMAIL)] ?: ""
    }
    
    val language: Flow<String> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(Constants.PREF_LANGUAGE)] ?: Constants.LANGUAGE_FRENCH
    }
    
    val darkMode: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey(Constants.PREF_DARK_MODE)] ?: false
    }
    
    val lastCleanTime: Flow<Long> = dataStore.data.map { prefs ->
        prefs[longPreferencesKey(Constants.PREF_LAST_CLEAN_TIME)] ?: 0L
    }
    
    val totalSpaceFreed: Flow<Long> = dataStore.data.map { prefs ->
        prefs[longPreferencesKey(Constants.PREF_TOTAL_SPACE_FREED)] ?: 0L
    }
    
    val totalRamFreed: Flow<Long> = dataStore.data.map { prefs ->
        prefs[longPreferencesKey(Constants.PREF_TOTAL_RAM_FREED)] ?: 0L
    }
    
    val autoCleanEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey(Constants.PREF_AUTO_CLEAN)] ?: false
    }
    
    val autoCleanInterval: Flow<Int> = dataStore.data.map { prefs ->
        prefs[intPreferencesKey(Constants.PREF_AUTO_CLEAN_INTERVAL)] ?: Constants.AUTO_CLEAN_DEFAULT_INTERVAL
    }
    
    // Save Preferences
    suspend fun saveUserId(userId: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(Constants.PREF_USER_ID)] = userId
        }
    }
    
    suspend fun saveUserEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(Constants.PREF_USER_EMAIL)] = email
        }
    }
    
    suspend fun setLanguage(language: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(Constants.PREF_LANGUAGE)] = language
        }
    }
    
    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(Constants.PREF_DARK_MODE)] = enabled
        }
    }
    
    suspend fun updateLastCleanTime(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[longPreferencesKey(Constants.PREF_LAST_CLEAN_TIME)] = timestamp
        }
    }
    
    suspend fun updateTotalSpaceFreed(space: Long) {
        dataStore.edit { prefs ->
            val current = prefs[longPreferencesKey(Constants.PREF_TOTAL_SPACE_FREED)] ?: 0L
            prefs[longPreferencesKey(Constants.PREF_TOTAL_SPACE_FREED)] = current + space
        }
    }
    
    suspend fun updateTotalRamFreed(ram: Long) {
        dataStore.edit { prefs ->
            val current = prefs[longPreferencesKey(Constants.PREF_TOTAL_RAM_FREED)] ?: 0L
            prefs[longPreferencesKey(Constants.PREF_TOTAL_RAM_FREED)] = current + ram
        }
    }
    
    suspend fun setAutoClean(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(Constants.PREF_AUTO_CLEAN)] = enabled
        }
    }
    
    suspend fun setAutoCleanInterval(minutes: Int) {
        dataStore.edit { prefs ->
            prefs[intPreferencesKey(Constants.PREF_AUTO_CLEAN_INTERVAL)] = minutes
        }
    }
    
    suspend fun clearUserData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
