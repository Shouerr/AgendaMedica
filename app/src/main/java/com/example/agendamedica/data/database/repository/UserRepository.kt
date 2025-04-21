package com.example.agendamedica.data.database.repository

//implementación autenticación
import android.util.Log
import com.example.agendamedica.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("usuarios")

    // Registrar usuario en Auth y guardar en Firestore
    suspend fun registerUser(email: String, password: String, nombre: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = auth.currentUser  // <- más confiable que result.user

            if (firebaseUser != null) {
                Log.d("UserRepository", "Usuario autenticado: ${firebaseUser.email}")
                val user = UserModel(
                    id = firebaseUser.uid,
                    nombre = nombre,
                    correoElectronico = email
                )
                registrarUsuario(user)
                firebaseUser
            } else {
                Log.e("UserRepository", "FirebaseUser es null después de registrar.")
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al registrar usuario: ${e.message}")
            null
        }
    }

    // Iniciar sesión con email y password
    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al iniciar sesión: ${e.message}")
            null
        }
    }

    // Cerrar sesión
    fun logout() {
        auth.signOut()
    }

    // Obtener usuario actual
    fun getCurrentUser(): FirebaseUser? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("AuthViewModel", "Current user: ${currentUser?.email ?: "No user"}")
        return currentUser
    }

    // Obtener usuario de Firestore
    suspend fun obtenerUsuario(uid: String): UserModel? {
        return try {
            usersCollection.document(uid).get().await().toObject(UserModel::class.java)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al obtener usuario: ${e.message}")
            null
        }
    }

    // Guardar usuario en Firestore
    private suspend fun registrarUsuario(user: UserModel) {
        try {
            usersCollection.document(user.id).set(user).await()
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al guardar usuario: ${e.message}")
        }
    }
}