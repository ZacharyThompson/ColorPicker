package me.zthompson.colorpicker

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(private val dataStore: DataStore<Preferences>) {
    private val RED_KEY = intPreferencesKey("red")
    private val GREEN_KEY = intPreferencesKey("green")
    private val BLUE_KEY = intPreferencesKey("blue")

    val red: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[RED_KEY] ?: 0
    }.distinctUntilChanged()
    val green: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[GREEN_KEY] ?: 0
    }.distinctUntilChanged()
    val blue: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[BLUE_KEY] ?: 0
    }.distinctUntilChanged()

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveColor(r: Int, g: Int, b: Int) {
        saveIntValue(RED_KEY, r)
        saveIntValue(GREEN_KEY, g)
        saveIntValue(BLUE_KEY, b)
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