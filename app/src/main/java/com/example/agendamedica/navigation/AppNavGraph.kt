package com.example.agendamedica.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agendamedica.ui.screens.* // trae todo de la carpeta de screens


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("cita") { CitaScreen(navController) }
        composable("perfil") { PerfilScreen(navController) }
    }
}