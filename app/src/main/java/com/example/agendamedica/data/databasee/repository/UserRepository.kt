package com.example.agendamedica.data.databasee.repository

//implementación autenticación
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Registrar usuario con email y password
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    // Iniciar sesión con email y password
    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    // Cerrar sesión
    fun logout() {
        auth.signOut()
    }

    // Obtener usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}