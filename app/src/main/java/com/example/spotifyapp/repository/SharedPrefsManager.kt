package com.example.spotifyapp.repository

import android.content.Context

class SharedPrefsManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun registerUser(username: String, password: String) {
        prefs.edit()
            .putString(username, password)
            .apply()
    }

    fun loginUser(username: String, password: String): Boolean {
        val savedPassword = prefs.getString(username, null)
        return savedPassword == password
    }

    fun isUserRegistered(username: String): Boolean {
        return prefs.contains(username)
    }
}