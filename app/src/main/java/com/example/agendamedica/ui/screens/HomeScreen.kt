package com.example.agendamedica.ui.screens

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
import androidx.compose.material.icons.filled.DarkMode
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
import androidx.navigation.NavController
import com.example.agendamedica.model.CitaModel
import com.example.agendamedica.viewmodel.AuthViewModel
import com.example.agendamedica.viewmodel.CitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    citaViewModel: CitaViewModel,
    toggleTheme: () -> Unit,

    ) {
    val userState by authViewModel.user.collectAsState()
    val citaState by citaViewModel.state.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var citaAEliminar by remember { mutableStateOf<CitaModel?>(null) }

    // ✅ Solo cargar citas una vez
    LaunchedEffect(Unit) {
        citaViewModel.cargarCitas()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Agenda Médica") },
                actions = {
                    IconButton(onClick = toggleTheme) {
                        Icon(
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = "Cambiar tema"
                        )
                    }
                }
            )
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
                    popUpTo(0)
                    launchSingleTop = true
                }
            }) {
                Text("Cerrar sesión")
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
                citaState.citas.forEach { cita ->
                    CitaCard(cita, navController, citaViewModel)
                }
            }
        }
    }

    // Diálogo para eliminar
    if (showDialog && citaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta cita?") },
            confirmButton = {
                Button(onClick = {
                    citaAEliminar?.let { citaViewModel.eliminarCita(it.idCita) }
                    showDialog = false
                }) {
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

@Composable
fun CitaCard(
    cita: CitaModel,
    navController: NavController,
    citaViewModel: CitaViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
                IconButton(onClick = {
                    navController.navigate("editarCita/${cita.idCita}")
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar Cita")
                }

                IconButton(onClick = {
                    citaViewModel.eliminarCita(cita.idCita)
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar Cita")
                }
            }
        }
    }
}
