package com.example.agendamedica.model

//Información de la cita
data class CitaModel(
    val idCita: String = "",
    val usuarioId: String = "",
    val fecha: String = "",
    val hora: String = "",
    val ubicacion:  Ubicacion = Ubicacion(),
    val medico: String = "",
    val motivo: String = "",
    val estado: String = "" //Confirmada, cancelada, completada
)

