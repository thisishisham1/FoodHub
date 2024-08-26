package iti.example.foodhub.sharedPref

import android.content.SharedPreferences
import android.util.Log

class SharedPrefHelper(private val sharedPreferences: SharedPreferences) {

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return try {
            sharedPreferences.getInt(key, defaultValue)
        } catch (e: ClassCastException) {
            Log.d("sharedpref", "getInt: ${e.message}")
            defaultValue
        }
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}