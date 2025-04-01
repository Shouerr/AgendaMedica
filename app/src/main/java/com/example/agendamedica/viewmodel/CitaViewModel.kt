package com.example.agendamedica.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendamedica.model.CitaModel
import com.example.agendamedica.model.Ubicacion
import com.example.agendamedica.ui.state.CitaState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CitaViewModel  : ViewModel() {

    private val _state = mutableStateOf(CitaState())
    val state: State<CitaState> = _state

    // Simulación de base de datos local (mientras no conectes Firebase)
    private val citasSimuladas = mutableListOf<CitaModel>()

    // Función para agregar una cita
    fun agregarCita(
        fecha: String,
        hora: String,
        provincia: String,
        hospital: String,
        medico: String,
        motivo: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val nuevaCita = CitaModel(
                idCita = System.currentTimeMillis().toString(),
                fecha = fecha,
                hora = hora,
                ubicacion = Ubicacion(provincia, hospital),
                medico = medico,
                motivo = motivo,
                estado = "confirmada"
            )

            citasSimuladas.add(nuevaCita)

            _state.value = _state.value.copy(
                isLoading = false,
                citas = citasSimuladas.toList()
            )
        }
    }

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