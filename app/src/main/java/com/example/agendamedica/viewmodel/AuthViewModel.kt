package com.example.agendamedica.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendamedica.data.database.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository = UserRepository()) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> get() = _user

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> get() = _authError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        _user.value = userRepository.getCurrentUser()
    }

    // Registrar usuario con email, password y nombre
    fun register(email: String, password: String, nombre: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val newUser = userRepository.registerUser(email, password, nombre)
            _isLoading.value = false

            if (newUser != null) {
                _user.value = newUser
                _authError.value = null
            } else {
                _authError.value = "Error al registrar usuario"
            }
        }
    }

    // Iniciar sesión con email y password
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val loggedInUser = userRepository.loginUser(email, password)
            _isLoading.value = false

            if (loggedInUser != null) {
                _user.value = loggedInUser
                _authError.value = null
            } else {
                _authError.value = "Error al iniciar sesión"
            }
        }
    }

    // Cerrar sesión
    fun logout() {
        userRepository.logout()
        _user.value = null
    }

    // Resetear el mensaje de error (útil al cambiar de pantalla o corregir inputs)
    fun resetError() {
        _authError.value = null
    }
}


