package com.example.agendamedica.viewmodel

import android.util.Log
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
            Log.d("AuthViewModel", "Inicio del registro...")

            try {
                val newUser = userRepository.registerUser(email, password, nombre)

                // Después de intentar registrar el usuario, restablecer el estado de carga
                _isLoading.value = false

                if (newUser != null) {
                    // Actualiza el estado de _user con el FirebaseUser directamente
                    _user.value = newUser // Aquí asignamos el FirebaseUser a _user
                    _authError.value = null
                    Log.d("AuthViewModel", "Registro completado.")
                } else {
                    _authError.value = "El usuario ya existe"
                    Log.e("AuthViewModel", "Usuario no registrado: Usuario ya existe.")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _authError.value = "Error al registrar usuario: ${e.message}"
                Log.e("AuthViewModel", "Error al registrar usuario: ${e.message}")
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
                Log.d("AuthViewModel", "Login exitoso: ${loggedInUser.email}")
            } else {
                _authError.value = "Usuario no registrado"
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


