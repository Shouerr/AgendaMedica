package com.example.agendamedica.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

//ruta del Auth Repository
import com.example.agendamedica.data.databasee.repository.AuthRepository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _user = MutableStateFlow <FirebaseUser?>(authRepository.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val loggedInUser = authRepository.loging(email, password)
            _user.value = loggedInUser
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val registeredUser = authRepository.register(email, password)
            _user.value = registeredUser
        }
    }

    fun logout() {
        authRepository.logout()
        _user.value = null
    }



}