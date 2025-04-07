package com.example.agendamedica.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agendamedica.ui.screens.CitaScreen
import com.example.agendamedica.ui.screens.HomeScreen
import com.example.agendamedica.ui.screens.LoginScreen
import com.example.agendamedica.ui.screens.PerfilScreen
import com.example.agendamedica.ui.screens.RegistroScreen
import com.example.agendamedica.viewmodel.AuthViewModel


@Composable
fun AppNavGraph(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    val userState by authViewModel.user.collectAsState()

    // Si el estado del usuario cambia, manejamos la navegación
    if (userState != null) {
        LaunchedEffect(userState) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = if (userState != null) "home" else "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("perfil") { PerfilScreen(navController) }
        composable("cita") { CitaScreen(navController) }
    }
}