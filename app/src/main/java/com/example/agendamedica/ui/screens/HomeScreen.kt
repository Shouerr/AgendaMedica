package com.example.agendamedica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    //
    @Composable
    fun CitaCard(
        cita: CitaModel,
        navController: NavController,
        citaViewModel: CitaViewModel,
        onSolicitarEliminar: (CitaModel) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), // Más compacto
            shape = RoundedCornerShape(6.dp), // Un poco más ajustado
            elevation = CardDefaults.elevatedCardElevation(2.dp) // Menor sombra
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "📅 ${cita.fecha}  🕒 ${cita.hora}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row {
                        IconButton(onClick = {
                            navController.navigate("editarCita/${cita.idCita}")
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Editar Cita")
                        }

                        IconButton(onClick = {
                            onSolicitarEliminar(cita) // ✅ en lugar de eliminar directo
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar Cita")
                        }
                    }
                }

                Text(
                    "📍 ${cita.ubicacion.provincia} - ${cita.ubicacion.hospital}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text("📝 Motivo: ${cita.motivo}", style = MaterialTheme.typography.bodyLarge)
                Text("👨‍⚕️ Médico: ${cita.medico}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    LaunchedEffect(Unit) {
        citaViewModel.cargarCitas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda Médica") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Bienvenido a tu Agenda Médica",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { navController.navigate("cita") }) {
                    Text("Agendar nueva cita")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(onClick = { navController.navigate("perfil") }) {
                    Text("Ver perfil")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(onClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }) {
                    Text("Cerrar sesión")
                }


                Spacer(modifier = Modifier.height(35.dp))

                Text("Tus citas:", style = MaterialTheme.typography.titleLarge)
                //Spacer(modifier = Modifier.height(2.dp))
            }

            when {
                citaState.isLoading -> item {
                    CircularProgressIndicator()
                }

                citaState.citas.isEmpty() -> item {
                    Text("No hay citas registradas.")
                }

                else -> {
                    // Agrupar por mes
                    val citasAgrupadas = citaState.citas
                        .sortedBy {
                            it.fecha.split("/").let { partes ->
                                val day = partes[0].padStart(2, '0')
                                val month = partes[1].padStart(2, '0')
                                val year = partes[2]
                                "$year$month$day".toInt()
                            }
                        }
                        .groupBy { cita ->
                            val partes = cita.fecha.split("/")
                            val month = partes[1].toInt()
                            val year = partes[2]
                            val nombreMes = listOf(
                                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
                            )[month - 1]
                            "$nombreMes $year"
                        }

                    citasAgrupadas.forEach { (mesAnio, citas) ->
                        item {
                            Text(
                                text = mesAnio,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(citas, key = { it.idCita }) { cita ->
                            CitaCard(
                                cita = cita,
                                navController = navController,
                                citaViewModel = citaViewModel,
                                onSolicitarEliminar = {
                                    citaAEliminar = it
                                    showDialog = true
                                }
                            )
                        }
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
}

//comentario para commit


