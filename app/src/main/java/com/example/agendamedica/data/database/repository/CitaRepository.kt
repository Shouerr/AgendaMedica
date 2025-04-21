package com.example.agendamedica.data.database.repository

import com.example.agendamedica.data.database.interfaces.CitaDao
import com.example.agendamedica.model.CitaModel
import com.google.firebase.auth.FirebaseAuth

class CitaRepository(private val citaDao: CitaDao) {

    // Agregar una nueva cita
    suspend fun agregarCita(cita: CitaModel) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: return
        citaDao.agregarCita(cita)  // Llamada a CitaDao para agregar
    }

    // Obtener las citas de un usuario
    suspend fun obtenerCitas(usuarioId: String): List<CitaModel> {
        return citaDao.obtenerCita(usuarioId)  // Llamada a CitaDao para obtener citas
    }

    // Actualizar una cita
    suspend fun actualizarCita(cita: CitaModel) {
        citaDao.actualizarCita(cita)  // Llamada a CitaDao para actualizar
    }

    // Eliminar una cita
    suspend fun eliminarCita(idCita: String) {
        citaDao.eliminarCita(idCita)  // Llamada a CitaDao para eliminar
    }
}