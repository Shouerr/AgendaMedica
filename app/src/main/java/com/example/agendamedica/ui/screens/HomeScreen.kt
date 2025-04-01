package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendamedica.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val userState = authViewModel.user.collectAsState(initial = null).value

    //Redirigir al login si el usuario no está autenticado
    LaunchedEffect(userState) {
        if (userState == null) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true } //Evita volver atrás
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Agenda Médica") }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bienvenido a Agenda Médica", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { authViewModel.logout() }) {
                Text("Cerrar Sesión")
            }
        }
    }
}