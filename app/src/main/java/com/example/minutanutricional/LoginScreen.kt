package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToRecuperarContrasena: () -> Unit,
    onNavigateToCrearCuenta: () -> Unit,
    onNavigateToMinutaNutricional: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            errorMessage = "Por favor, completa todos los campos"
                            showErrorDialog = true
                        }
                        !Utils.isValidEmail(email) -> {
                            errorMessage = "Por favor, introduce un correo electrónico válido"
                            showErrorDialog = true
                        }
                        else -> {
                            try {
                                if (CuentasManager.validarCredenciales(email, password)) {
                                    onNavigateToMinutaNutricional(email)
                                } else {
                                    errorMessage = "Correo electrónico o contraseña incorrectos"
                                    showErrorDialog = true
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error al iniciar sesión. Por favor, intenta nuevamente."
                                showErrorDialog = true
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToCrearCuenta) {
            Text("Crear cuenta nueva")
        }

        TextButton(onClick = onNavigateToRecuperarContrasena) {
            Text("¿Has olvidado la contraseña?")
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToRecuperarContrasena = {},
        onNavigateToCrearCuenta = {},
        onNavigateToMinutaNutricional = {}
    )
}