package com.example.agendamedica.data.database

import com.google.firebase.firestore.FirebaseFirestore

class CitaDatabase {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Proporciona una referencia a la colección de citas en Firestore
    fun getCitasCollection() = db.collection("citas")
}