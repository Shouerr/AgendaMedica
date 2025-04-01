package com.example.agendamedica.ui.state

import com.example.agendamedica.model.UserModel

data class UserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: UserModel? = null
)