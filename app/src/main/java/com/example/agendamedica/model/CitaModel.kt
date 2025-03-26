package com.example.agendamedica.model

data class CitaModel(
    val idCita: String = "",
    val fecha: String = "",
    val hora: String = "",
    val ubicacion: String = "",
    val medico: String = "",
    val motivoConsulta: String = "",
    val estadoCita: String = "",
    val provincia: String = "",
    val hospital: String = ""
)
