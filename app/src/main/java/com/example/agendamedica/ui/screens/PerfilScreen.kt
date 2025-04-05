package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendamedica.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PerfilScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()) {
    val state by userViewModel.state.collectAsState()

    // Cargar perfil al entrar
    LaunchedEffect(Unit) {
        userViewModel.cargarPerfil()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario")},
                navigationIcon = {}, // vacío si no tenés ícono
                actions = {}         // vacío si no tenés acciones
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.error != null -> {
                    Text(text = "${state.error}", color = MaterialTheme.colorScheme.error)
                }

                state.user != null -> {
                    val user = state.user!! // O mejor, val user = state.user (y usás smart cast)

                    Text(text = "Nombre: ${user.nombre}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Correo: ${user.correoElectronico}")
                }
            }
        }
    }
}