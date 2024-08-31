package com.example.minutanutricional

import java.util.*
import java.text.SimpleDateFormat
import java.util.Locale

data class Cuenta(
    val nombre: String,
    val apellidos: String,
    val correoElectronico: String,
    val contrasena: String,
    val fechaNacimiento: Date
)

object CuentasManager {
    private val cuentas = mutableListOf<Cuenta>()

    fun agregarCuenta(cuenta: Cuenta): Boolean {
        if (buscarCuentaPorCorreo(cuenta.correoElectronico) != null) {
            return false // El correo ya está en uso
        }
        cuentas.add(cuenta)
        return true
    }

    fun validarCredenciales(correoElectronico: String, contrasena: String): Boolean {
        return cuentas.any { it.correoElectronico == correoElectronico && it.contrasena == contrasena }
    }

    fun buscarCuentaPorCorreo(correoElectronico: String): Cuenta? {
        return cuentas.find { it.correoElectronico == correoElectronico }
    }

    // Función para parsear una fecha en formato String a Date
    private fun parsearFecha(fecha: String): Date {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formato.parse(fecha) ?: Date() // Si hay un error en el parseo, devuelve la fecha actual
    }

    // Función para agregar algunas cuentas de prueba
    fun agregarCuentasDePrueba() {
        agregarCuenta(Cuenta("User", "Test", "test@test.com", "123456", parsearFecha("1990-01-01")))
        agregarCuenta(Cuenta("Jaime", "Zapata Salinas", "jzapata@crell.cl", "123456", parsearFecha("1993-05-22")))
        agregarCuenta(Cuenta("Miguel", "Puebla Cuero", "mpuebla@test.com", "123456", parsearFecha("1990-01-01")))
    }
}