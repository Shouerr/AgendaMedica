package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.agendamedica.viewmodel.CitaViewModel

@Composable
fun CitaScreen(navController: NavHostController, viewModel: CitaViewModel = hiltViewModel()) {
    val state by viewModel.state

    if (state.isLoading) {
        CircularProgressIndicator()
    } else if (state.error != null) {
        Text("Error: ${state.error}")
    } else {
        LazyColumn {
            items(state.citas) { cita ->
                Text(text = cita.motivo)
            }
        }
    }
}