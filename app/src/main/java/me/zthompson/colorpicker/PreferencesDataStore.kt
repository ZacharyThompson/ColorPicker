package me.zthompson.colorpicker

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(private val dataStore: DataStore<Preferences>) {
    private val COLOR_KEY = intPreferencesKey("color")
    private val RED_ENABLED_KEY = booleanPreferencesKey("red_enabled")
    private val GREEN_ENABLED_KEY = booleanPreferencesKey("green_enabled")
    private val BLUE_ENABLED_KEY = booleanPreferencesKey("blue_enabled")

    val color: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[COLOR_KEY] ?: 0
    }.distinctUntilChanged()

    val redEnabled: Flow<Boolean> = this.dataStore.data.map { prefs ->
        prefs[RED_ENABLED_KEY] ?: true
    }
    val greenEnabled: Flow<Boolean> = this.dataStore.data.map { prefs ->
        prefs[GREEN_ENABLED_KEY] ?: true
    }.distinctUntilChanged()
    val blueEnabled: Flow<Boolean> = this.dataStore.data.map { prefs ->
        prefs[BLUE_ENABLED_KEY] ?: true
    }.distinctUntilChanged()

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
    private suspend fun saveBoolValue(key: Preferences.Key<Boolean>, value: Boolean) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveColor(c: Int) {
        saveIntValue(COLOR_KEY, c)
    }

    suspend fun saveToggles(r: Boolean, g: Boolean, b: Boolean) {
        saveBoolValue(RED_ENABLED_KEY, r)
        saveBoolValue(GREEN_ENABLED_KEY, g)
        saveBoolValue(BLUE_ENABLED_KEY, b)
    }


    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "settings"
        private var INSTANCE: PreferencesRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = PreferencesRepository(dataStore)
            }
        }

        fun getRepository(): PreferencesRepository {
            return INSTANCE ?: throw IllegalStateException("AppPreferencesRepository not initialized yet")
        }
    }
}