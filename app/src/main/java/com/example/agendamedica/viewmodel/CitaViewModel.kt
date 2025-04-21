package com.example.agendamedica.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendamedica.model.CitaModel
import com.example.agendamedica.model.Ubicacion
import com.example.agendamedica.ui.state.CitaState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CitaViewModel  : ViewModel() {

    private val _state = MutableStateFlow(CitaState())
    val state: StateFlow<CitaState> get() = _state

    private val db = FirebaseFirestore.getInstance()
    private val citasCollection = db.collection("citas")

    // Función para agregar una cita
    fun agregarCita(
        fecha: String,
        hora: String,
        provincia: String,
        hospital: String,
        medico: String,
        motivo: String,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val nuevaCita = CitaModel(
                idCita = System.currentTimeMillis().toString(),
                fecha = fecha,
                hora = hora,
                ubicacion = Ubicacion(provincia, hospital),
                medico = medico,
                motivo = motivo,
                usuarioId = userId // ✅ ahora la cita se asocia al usuario actual
            )

            try {
                citasCollection.document(nuevaCita.idCita).set(nuevaCita).await()
                cargarCitas()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error al guardar cita: ${e.message}")
            }

            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun cargarCitas() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid ?: return@launch

            try {
                val snapshot = citasCollection
                    .whereEqualTo("usuarioId", userId) // 👈 filtro
                    .get()
                    .await()

                val citas = snapshot.toObjects(CitaModel::class.java)
                _state.value = _state.value.copy(citas = citas, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al cargar citas: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    // Función para eliminar una cita
    fun eliminarCita(idCita: String) {
        viewModelScope.launch {
            try {
                citasCollection.document(idCita).delete().await()
                cargarCitas() // Recargar las citas después de la eliminación
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error al eliminar cita: ${e.message}")
            }
        }
    }


    fun cargarCitaPorId(idCita: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid ?: return@launch

            try {
                val snapshot = citasCollection
                    .whereEqualTo("idCita", idCita)
                    .whereEqualTo("usuarioId", userId) // 👈 validación de seguridad
                    .get()
                    .await()

                val citas = snapshot.toObjects(CitaModel::class.java)
                _state.value = _state.value.copy(citas = citas, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al cargar cita: ${e.message}",
                    isLoading = false
                )
            }
        }
    }


    fun actualizarCita(
        idCita: String,
        fecha: String,
        hora: String,
        provincia: String,
        hospital: String,
        motivo: String,
        medico: String
    ) {
        viewModelScope.launch {
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.uid ?: return@launch  //btenemos el usuario actual

                val citaActualizada = CitaModel(
                    idCita = idCita,
                    fecha = fecha,
                    hora = hora,
                    ubicacion = Ubicacion(provincia, hospital),
                    medico = medico,
                    motivo = motivo,
                    estado = "confirmada",
                    usuarioId = userId //para que siga asociada al usuario
                )

                citasCollection.document(idCita).set(citaActualizada).await()
                cargarCitas()  // Recargar las citas después de actualizar
            } catch (e: Exception) {
                Log.e("CitaViewModel", "Error al actualizar cita: ${e.message}")
            }
        }
    }
}