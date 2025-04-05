package com.example.agendamedica.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendamedica.data.database.repository.UserRepository
import com.example.agendamedica.ui.state.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> get() = _state

    fun cargarPerfil() {
        val currentUser = userRepository.getCurrentUser()
        if (currentUser == null) {
            _state.value = UserState(error = "Usuario no autenticado")
            return
        }

        viewModelScope.launch {
            _state.value = UserState(isLoading = true)
            val user = userRepository.obtenerUsuario(currentUser.uid)
            if (user != null) {
                _state.value = UserState(user = user)
            } else {
                _state.value = UserState(error = "No se pudo cargar el perfil")
            }
        }
    }
}
