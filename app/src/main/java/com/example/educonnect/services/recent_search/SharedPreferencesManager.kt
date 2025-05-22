package com.example.educonnect.services.recent_search

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class SearchPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val maxRecentSearches = 5

    fun saveRecentSearches(userId: String?, searches: List<String>) {
        val json = gson.toJson(searches)
        sharedPreferences.edit().putString("${userId}_recent_searches", json).apply()
    }

    fun getRecentSearches(userId: String?): List<String> {
        val json = sharedPreferences.getString("${userId}_recent_searches", null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addRecentSearch(userId: String?, searchTerm: String) {
        val currentSearches = getRecentSearches(userId).toMutableList()
        currentSearches.remove(searchTerm)
        currentSearches.add(0, searchTerm)
        if (currentSearches.size > maxRecentSearches) {
            currentSearches.removeAt(currentSearches.size - 1)
        }
        saveRecentSearches(userId, currentSearches)
    }

    fun removeRecentSearch(userId: String?, searchTerm: String) {
        val currentSearches = getRecentSearches(userId).toMutableList()
        currentSearches.remove(searchTerm)
        saveRecentSearches(userId, currentSearches)
    }

    fun clearRecentSearches(userId: String?) {
        sharedPreferences.edit().remove("${userId}_recent_searches").apply()
    }
}