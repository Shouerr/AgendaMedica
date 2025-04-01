package com.example.agendamedica.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.agendamedica.ui.state.CitaState
import com.example.agendamedica.model.CitaModel

class CitaViewModel {

    private val _state = mutableStateOf(CitaState())
    val state: State<CitaState> = _state

    fun cargarCitas() {
        _state.value = _state.value.copy(isLoading = true)
        // Simulación
        viewModelScope.launch {
            delay(1000)
            _state.value = _state.value.copy(
                isLoading = false,
                citas = listOf(/* tus citas cargadas */)
            )
        }
    }
}