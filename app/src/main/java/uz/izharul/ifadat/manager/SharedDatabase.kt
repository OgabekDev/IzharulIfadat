package uz.izharul.ifadat.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedDatabase(context: Context) {

    private val pref = context.getSharedPreferences("database.db", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String?) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun saveInt(key: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    fun saveFloat(key: String, value: Float) {
        val editor = pref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String): Float {
        return pref.getFloat(key, 0F)
    }

    fun saveLanguage(language: String) {
        val editor = pref.edit()
        editor.putString("language", language)
        editor.apply()
    }

    fun getLanguage(): String? {
        return pref.getString("language", null)
    }

}

class DataStoreManager(private val context: Context) {


    private val Context.dataStore by preferencesDataStore("Database")

    companion object {
        val theme = booleanPreferencesKey("isDarkMode")
    }

    suspend fun saveTheme(isDarkMode: Boolean) {
        context.dataStore.edit {
            it[theme] = isDarkMode
        }
    }

    fun getTheme(): Flow<Boolean?> {
        return context.dataStore.data.map {
            it[theme]
        }
    }

}