package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetalleFragment(recetaId: Int, navController: NavController) {
    val receta = recetasSemana.find { it.id == recetaId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles de la Receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Cerrar")
                    }
                }
            )
        }
    ) { innerPadding ->
        receta?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = it.nombre, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it.descripcion, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium)
                it.ingredientes.forEach { ingrediente ->
                    Text("• $ingrediente")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Preparación:", style = MaterialTheme.typography.titleMedium)
                Text(text = it.preparacion)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Recomendación nutricional:", style = MaterialTheme.typography.titleMedium)
                Text(text = it.recomendacionNutricional)

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Cerrar")
                }
            }
        } ?: Text("Receta no encontrada")
    }
}