package com.example.minutanutricional

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Cuenta(
    val nombre: String = "",
    val apellidos: String = "",
    val correoElectronico: String = "",
    val contrasena: String = "",
    val fechaNacimiento: Long = 0
)

object CuentasManager {
    private val database = FirebaseDatabase.getInstance()
    private val cuentasRef = database.getReference("cuentas")
    private val auth = FirebaseAuth.getInstance()

    suspend fun agregarCuenta(cuenta: Cuenta): Boolean = withContext(Dispatchers.IO) {
        try {
            val result = auth.createUserWithEmailAndPassword(cuenta.correoElectronico, cuenta.contrasena).await()
            val userId = result.user?.uid ?: return@withContext false
            cuentasRef.child(userId).setValue(cuenta).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun validarCredenciales(correoElectronico: String, contrasena: String): Boolean = withContext(Dispatchers.IO) {
        try {
            auth.signInWithEmailAndPassword(correoElectronico, contrasena).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun buscarCuentaPorCorreo(correoElectronico: String): Cuenta? = withContext(Dispatchers.IO) {
        try {
            val snapshot = cuentasRef.orderByChild("correoElectronico").equalTo(correoElectronico).get().await()
            snapshot.children.firstOrNull()?.getValue(Cuenta::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun agregarCuentasDePrueba() {
        val cuentasPrueba = listOf(
            Cuenta("User", "Test", "test@test.com", "123456", System.currentTimeMillis()),
            Cuenta("Jaime", "Zapata Salinas", "jzapata@crell.cl", "123456", System.currentTimeMillis()),
            Cuenta("Miguel", "Puebla Cuero", "mpuebla@test.com", "123456", System.currentTimeMillis())
        )

        cuentasPrueba.forEach { cuenta ->
            agregarCuenta(cuenta)
        }
    }
}