package com.example.agendamedica.model

//Información de cada usuario
data class UserModel(
    val id: String = "",
    val nombre: String = "",
    val correoElectronico: String = "",
    val fotoPerfil: String? = null,
    val preferenciasNotificacion: Boolean = true
)

