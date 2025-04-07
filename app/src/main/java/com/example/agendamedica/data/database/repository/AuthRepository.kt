package com.example.agendamedica.data.database.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Obtener el usuario actual
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    //Iniciar sesión con correo y contraseña
    suspend fun loging(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    //Registrar nuevo Usuario
    suspend fun register(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    //Cerrar Sesión
    fun logout() {
        auth.signOut()
    }
}