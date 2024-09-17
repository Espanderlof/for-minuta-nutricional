package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuentaScreen(onNavigateBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta Nueva", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotEmpty() && !Utils.isValidEmail(email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fechaNacimiento?.let { dateFormatter.format(Date(it)) } ?: "",
            onValueChange = { },
            label = { Text("Fecha de nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    if (validateFields(nombre, apellidos, email, password, fechaNacimiento)) {
                        val nuevaCuenta = Cuenta(nombre, apellidos, email, password, fechaNacimiento!!)
                        try {
                            if (CuentasManager.agregarCuenta(nuevaCuenta)) {
                                alertMessage = "Su cuenta ha sido creada con éxito"
                                showAlert = true
                            } else {
                                alertMessage = "El correo electrónico ya está en uso"
                                showAlert = true
                            }
                        } catch (e: Exception) {
                            alertMessage = "Error al crear la cuenta. Inténtalo de nuevo."
                            showAlert = true
                        }
                    } else {
                        alertMessage = "Por favor, complete todos los campos correctamente"
                        showAlert = true
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Crear cuenta")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateBack) {
            Text("Volver al inicio de sesión")
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = fechaNacimiento)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    fechaNacimiento = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text(if (alertMessage.contains("éxito")) "Éxito" else "Error") },
            text = { Text(alertMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showAlert = false
                    if (alertMessage.contains("éxito")) {
                        onNavigateBack()
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }
}

fun validateFields(nombre: String, apellidos: String, email: String, password: String, fechaNacimiento: Long?): Boolean {
    return nombre.isNotBlank() &&
            apellidos.isNotBlank() &&
            Utils.isValidEmail(email) &&
            password.isNotBlank() &&
            fechaNacimiento != null
}

@Preview(showBackground = true)
@Composable
fun CrearCuentaScreenPreview() {
    CrearCuentaScreen(
        onNavigateBack = {}
    )
}