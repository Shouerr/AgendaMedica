package com.example.agendamedica.ui.state

import com.example.agendamedica.model.CitaModel

data class CitaState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val citas: List<CitaModel> = emptyList()
)