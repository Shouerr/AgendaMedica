package com.example.agendamedica.ui.state

import com.example.agendamedica.model.CitaModel

data class CitaState(
    val isLoading: Boolean = false,
    val citas: List<CitaModel> = emptyList(),
    val error: String? = null
)