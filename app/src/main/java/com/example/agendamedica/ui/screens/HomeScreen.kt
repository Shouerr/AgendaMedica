package com.example.agendamedica.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    // Obtenemos el estado del usuario y las citas observadas con collectAsState()
    val userState = authViewModel.user.collectAsState().value
    val citaState by citaViewModel.state.collectAsState()

    // Estado para mostrar el diálogo de confirmación de eliminación
    var showDialog by remember { mutableStateOf(false) }
    var citaAEliminar by remember { mutableStateOf<CitaModel?>(null) }

    // Comprobamos si el usuario está logueado y cargamos las citas
    LaunchedEffect(userState) {
        if (userState == null) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true } // Limpiar la pila de navegación
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
            Text("Bienvenido a tu Agenda Médica", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { navController.navigate("cita") }) {
                Text("Agendar nueva cita")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                authViewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { navController.navigate("perfil") }) {
                Text("Ver perfil")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Tus citas:", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))

            if (citaState.isLoading) {
                CircularProgressIndicator()
            } else if (citaState.citas.isEmpty()) {
                Text("No hay citas registradas.")
            } else {
                // Aquí se crea una tarjeta para cada cita
                citaState.citas.forEach { cita: CitaModel ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), // Espacio entre tarjetas
                        shape = RoundedCornerShape(8.dp), // Bordes redondeados
                        elevation = CardDefaults.elevatedCardElevation(4.dp) // Sombra
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp) // Espacio dentro de la tarjeta
                        ) {
                            Text("Fecha: ${cita.fecha} - Hora: ${cita.hora}")
                            Text("Ubicación: ${cita.ubicacion.provincia} - ${cita.ubicacion.hospital}")
                            Text("Motivo: ${cita.motivo}")
                            Text("Médico: ${cita.medico}")

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                // Botón de Editar dentro de la tarjeta
                                IconButton(onClick = {
                                    Log.d("HomeScreen", "Editando cita con idCita: ${cita.idCita}")
                                    navController.navigate("editarCita/${cita.idCita}")
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Editar Cita"
                                    )
                                }

                                // Botón de Eliminar dentro de la tarjeta
                                IconButton(onClick = {
                                    citaViewModel.eliminarCita(cita.idCita)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Eliminar Cita"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación de eliminación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta cita?") },
            confirmButton = {
                Button(
                    onClick = {
                        citaAEliminar?.let {
                            citaViewModel.eliminarCita(it.idCita) // Eliminar la cita
                        }
                        showDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}





