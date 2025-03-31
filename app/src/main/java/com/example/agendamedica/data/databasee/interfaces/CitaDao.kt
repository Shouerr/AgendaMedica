package com.example.agendamedica.data.databasee.interfaces

import com.example.agendamedica.model.CitaModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CitaDao {
    private val db = FirebaseFirestore.getInstance()
    private val citasCollection = db.collection("citas")

    suspend fun agregarCita(cita: CitaModel) {
        citasCollection.document(cita.idCita).set(cita).await()
    }

    suspend fun obtenerCita(usuarioId: String): List<CitaModel> {
        return citasCollection.whereEqualTo("usuarioId", usuarioId)
            .get().await().toObjects(CitaModel::class.java)
    }

    suspend fun actualizarCita(cita: CitaModel) {
        citasCollection.document(cita.idCita).set(cita).await()
    }

    suspend fun eliminarCita(idCita: String) {
        citasCollection.document(idCita).delete().await()
    }
}