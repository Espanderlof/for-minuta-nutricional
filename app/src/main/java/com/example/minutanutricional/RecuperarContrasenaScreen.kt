package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecuperarContrasenaScreen(onNavigateBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Recuperar Contraseña", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
                errorMessage = ""
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || !Utils.isValidEmail(email)) {
                    isError = true
                    errorMessage = "Por favor, introduce un correo electrónico válido"
                } else {
                    if (CuentasManager.buscarCuentaPorCorreo(email) != null) {
                        dialogMessage = "Se ha enviado un correo de restauración a tu cuenta de correo electrónico $email"
                        showDialog = true
                    } else {
                        isError = true
                        errorMessage = "No se encontró ninguna cuenta asociada a este correo electrónico"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar correo de recuperación")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateBack) {
            Text("Volver al inicio de sesión")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Correo enviado") },
            text = { Text(dialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onNavigateBack()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecuperarContrasenaScreenPreview() {
    RecuperarContrasenaScreen(
        onNavigateBack = {}
    )
}