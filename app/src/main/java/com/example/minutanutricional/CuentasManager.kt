package com.example.minutanutricional

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

data class Cuenta(
    val nombre: String = "",
    val apellidos: String = "",
    val correoElectronico: String = "",
    val contrasena: String = "",
    val fechaNacimiento: Date = Date()
)

object CuentasManager {
    private val database = Firebase.database
    private val cuentasRef = database.getReference("cuentas")

    suspend fun agregarCuenta(cuenta: Cuenta): Boolean {
        return try {
            val cuentaKey = cuenta.correoElectronico.replace(".", ",")
            cuentasRef.child(cuentaKey).setValue(cuenta).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun validarCredenciales(correoElectronico: String, contrasena: String): Boolean {
        val cuentaKey = correoElectronico.replace(".", ",")
        val snapshot = cuentasRef.child(cuentaKey).get().await()
        val cuenta = snapshot.getValue(Cuenta::class.java)
        return cuenta?.contrasena == contrasena
    }

    suspend fun buscarCuentaPorCorreo(correoElectronico: String): Cuenta? {
        val cuentaKey = correoElectronico.replace(".", ",")
        val snapshot = cuentasRef.child(cuentaKey).get().await()
        return snapshot.getValue(Cuenta::class.java)
    }
}