package com.example.agendamedica.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agendamedica.ui.screens.CitaScreen
import com.example.agendamedica.ui.screens.EditarCitaScreen
import com.example.agendamedica.ui.screens.HomeScreen
import com.example.agendamedica.ui.screens.LoginScreen
import com.example.agendamedica.ui.screens.PerfilScreen
import com.example.agendamedica.ui.screens.RegistroScreen
import com.example.agendamedica.viewmodel.AuthViewModel
import com.example.agendamedica.viewmodel.CitaViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    toggleTheme: () -> Unit
){
    val userState by authViewModel.user.collectAsState()


    NavHost(
        navController = navController,
        startDestination = if (userState != null) "home" else "login"
    ) {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("registro") { RegistroScreen(navController, authViewModel) }
        composable("home") {
            val citaViewModel: CitaViewModel = hiltViewModel()
            HomeScreen(navController, authViewModel, citaViewModel, toggleTheme)
        }
        composable("perfil") { PerfilScreen(navController) }
        composable("cita") { CitaScreen(navController) }
        composable("editarCita/{idCita}") { backStackEntry ->
            val idCita = backStackEntry.arguments?.getString("idCita") ?: ""
            EditarCitaScreen(navController = navController, idCita = idCita)
        }
    }
}