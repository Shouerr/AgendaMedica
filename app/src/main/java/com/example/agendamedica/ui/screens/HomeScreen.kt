package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendamedica.model.CitaModel
import com.example.agendamedica.viewmodel.AuthViewModel
import com.example.agendamedica.viewmodel.CitaViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    citaViewModel: CitaViewModel = viewModel()
) {
    val userState = authViewModel.user.collectAsState().value
    val citaState = citaViewModel.state.value

    LaunchedEffect(userState) {
        if (userState == null) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true } // Asegúrate de limpiar la pila de navegación
            }
        } else {
            citaViewModel.cargarCitas()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Agenda Médica") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Bienvenido a Agenda Médica", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            userState?.email?.let {
                Text("Sesión iniciada como: $it")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("perfil") }) {
                Text("Ver perfil")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { navController.navigate("cita") }) {
                Text("Agendar nueva cita")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                authViewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de citas
            Text("Tus citas:", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (citaState.isLoading) {
                CircularProgressIndicator()
            } else if (citaState.citas.isEmpty()) {
                Text("No hay citas registradas.")
            } else {
                citaState.citas.forEach { cita: CitaModel ->
                    Text(" ${cita.fecha} - ${cita.hora}")
                    Text(" ${cita.ubicacion.provincia} - ${cita.ubicacion.hospital}")
                    Text(" ${cita.motivo}")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Botón de Editar
                        IconButton(onClick = {
                            // Lógica para editar la cita
                            navController.navigate("editarCita/${cita.idCita}")
                        }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar Cita")
                        }

//                        // Botón de Eliminar
//                        IconButton(onClick = {
//                            // Lógica para eliminar la cita
//                            citaViewModel.eliminarCita(cita.idCita)
//                        }) {
//                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar Cita")
//                        }
                    }
                }
            }
        }
    }
}


