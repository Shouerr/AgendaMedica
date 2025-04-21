package com.example.agendamedica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.agendamedica.navigation.AppNavGraph
import com.example.agendamedica.ui.theme.AgendaMedicaTheme
import com.example.agendamedica.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            val navController = rememberNavController()
            val authViewModel: AuthViewModel = hiltViewModel()

            AgendaMedicaTheme(darkTheme = isDarkTheme) {
                AppNavGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    toggleTheme = {
                        isDarkTheme = !isDarkTheme
                    } // <-- si querés manejar tema desde botón
                )
            }
        }
    }
}
