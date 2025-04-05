package com.example.agendamedica.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendamedica.model.CitaModel
import com.example.agendamedica.model.Ubicacion
import com.example.agendamedica.ui.state.CitaState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CitaViewModel  : ViewModel() {

    private val _state = mutableStateOf(CitaState())
    val state: State<CitaState> = _state

    private val db = FirebaseFirestore.getInstance()
    private val citasCollection = db.collection("citas")

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

            try {
                citasCollection.document(nuevaCita.idCita).set(nuevaCita).await()
                cargarCitas() // recarga desde Firebase
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error al guardar cita: ${e.message}")
            }

            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun cargarCitas() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val snapshot = citasCollection.get().await()
                val citas = snapshot.toObjects(CitaModel::class.java)
                _state.value = _state.value.copy(citas = citas, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error al cargar citas: ${e.message}", isLoading = false)
            }
        }
    }
}