package com.example.agendamedica.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendamedica.viewmodel.AuthViewModel
import com.example.agendamedica.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PerfilScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val userState by userViewModel.state.collectAsState()

    val user by authViewModel.user.collectAsState()

    //Al entrar, cargamos el perfil desde Firestore
    LaunchedEffect(Unit) {
        userViewModel.cargarPerfil()
    }

    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when {
            userState.isLoading -> {
                CircularProgressIndicator()
            }

            userState.error != null -> {
                Text("Error: ${userState.error}", color = MaterialTheme.colorScheme.error)
            }

            userState.user != null -> {
                val usuario = userState.user!!

                // Foto de perfil de ejemplo (prototipo)
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = usuario.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = usuario.correoElectronico,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    // futuro: cambiar foto de perfil
                }) {
                    Text("Cambiar foto de perfil")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Botón de cerrar sesión
//                Button(onClick = {
//                    authViewModel.logout()
//                }) {
//                    Text("Cerrar sesión")
//                }
            }
        }
    }
}
