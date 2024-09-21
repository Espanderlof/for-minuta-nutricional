package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class RecetaDiaria(
    val id: Int = 0,
    val fecha: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val recomendacionNutricional: String = "",
    val ingredientes: List<String> = listOf(),
    val preparacion: String = ""
) {
    fun fechaLocalDate(): LocalDate = LocalDate.parse(fecha)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaNutricionalScreen(navController: NavController, onLogout: () -> Unit) {
    var recetas by remember { mutableStateOf<List<RecetaDiaria>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        recetas = obtenerRecetasDeFirebase()
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta Nutricional Semanal") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(recetas) { receta ->
                    RecetaItem(
                        receta = receta,
                        onClick = {
                            navController.navigate("receta_detalle/${receta.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecetaItem(receta: RecetaDiaria, onClick: () -> Unit) {
    val localeES = Locale("es", "ES")
    val fecha = receta.fechaLocalDate()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${fecha.dayOfWeek.getDisplayName(TextStyle.FULL, localeES).capitalize()}, ${fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = receta.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recomendación nutricional: ${receta.recomendacionNutricional}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

suspend fun obtenerRecetasDeFirebase(): List<RecetaDiaria> {
    val database = Firebase.database
    val recetasRef = database.getReference("recetas")
    val snapshot = recetasRef.get().await()

    return snapshot.children.mapNotNull { recetaSnapshot ->
        recetaSnapshot.getValue(RecetaDiaria::class.java)
    }.sortedBy { it.fechaLocalDate() }
}

fun String.capitalize() = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }