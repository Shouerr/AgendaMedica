package com.example.agendamedica.data.database.repository

//implementación autenticación
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
            result.user?.let { firebaseUser ->
                val user = UserModel(
                    id = firebaseUser.uid, // ID del usuario en Auth
                    nombre = nombre,
                    correoElectronico = email
                )
                registrarUsuario(user) // Guardar en Firestore
            }
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

    // Obtener usuario de Firestore
    suspend fun obtenerUsuario(uid: String): UserModel? {
        return usersCollection.document(uid).get().await().toObject(UserModel::class.java)
    }

    // Guardar usuario en Firestore
    private suspend fun registrarUsuario(user: UserModel) {
        usersCollection.document(user.id).set(user).await()
    }
}