package com.example.agendamedica.userinterface.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agendamedica.ui.theme.screens.LoginScreen
import com.example.agendamedica.ui.theme.screens.RegistroScreen
import com.example.agendamedica.ui.theme.screens.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}