package com.example.spotifyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spotifyapp.repository.SharedPrefsManager

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefsManager = SharedPrefsManager(application)

    private val _authResult = MutableLiveData<String>()
    val authResult: LiveData<String> = _authResult

    fun register(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _authResult.value = "error_fields"
            return
        }
        if (sharedPrefsManager.isUserRegistered(username)) {
            _authResult.value = "error_exists"
            return
        }
        sharedPrefsManager.registerUser(username, password)
        _authResult.value = "register_ok"
    }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _authResult.value = "error_fields"
            return
        }
        if (!sharedPrefsManager.loginUser(username, password)) {
            _authResult.value = "error_credentials"
            return
        }
        _authResult.value = "login_ok"
    }
}