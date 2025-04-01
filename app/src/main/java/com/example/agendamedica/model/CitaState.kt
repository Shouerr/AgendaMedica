package com.example.agendamedica.model

data class CitaState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val citas: List<CitaModel> = emptyList()
)
