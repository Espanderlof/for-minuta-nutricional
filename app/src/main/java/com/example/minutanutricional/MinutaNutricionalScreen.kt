package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

data class RecetaDiaria(
    val fecha: LocalDate,
    val nombre: String,
    val descripcion: String
)

@Composable
fun MinutaNutricionalScreen() {
    val recetas = remember { generarMinutaMensual() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Minuta Nutricional Mensual",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(recetas) { receta ->
                RecetaItem(receta)
            }
        }
    }
}

@Composable
fun RecetaItem(receta: RecetaDiaria) {
    val localeES = Locale("es", "ES")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${receta.fecha.dayOfWeek.getDisplayName(TextStyle.FULL, localeES).capitalize()}, ${receta.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
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
        }
    }
}

fun generarMinutaMensual(): List<RecetaDiaria> {
    val hoy = LocalDate.now()
    val finPeriodo = hoy.plusDays(30) // 30 días a partir de hoy

    val recetas = mutableListOf<RecetaDiaria>()
    var fechaActual = hoy

    val opcionesRecetas = listOf(
        "Pollo al horno con verduras y arroz integral",
        "Salmón a la plancha con ensalada mixta",
        "Pasta integral con salsa de tomate y albahaca",
        "Lentejas con verduras y arroz",
        "Tortilla de espinacas y champiñones",
        "Ensalada César con pollo a la parrilla",
        "Paella de mariscos con arroz integral",
        "Sopa de verduras con crutones integrales",
        "Pavo asado con puré de papas",
        "Hamburguesa de lentejas con ensalada",
        "Pescado al vapor con brócoli y quinoa",
        "Lasaña de vegetales",
        "Curry de garbanzos con arroz basmati",
        "Revuelto de tofu con champiñones",
        "Estofado de ternera con verduras"
    )

    while (!fechaActual.isAfter(finPeriodo)) {
        val recetaDelDia = opcionesRecetas.random()
        recetas.add(RecetaDiaria(
            fecha = fechaActual,
            nombre = "Receta del día: $recetaDelDia",
            descripcion = "Disfruta de esta deliciosa receta saludable."
        ))
        fechaActual = fechaActual.plusDays(1)
    }

    return recetas
}

fun String.capitalize() = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

@Preview(showBackground = true)
@Composable
fun MinutaNutricionalScreenPreview() {
    MinutaNutricionalScreen()
}

@Preview(showBackground = true)
@Composable
fun RecetaItemPreview() {
    val recetaEjemplo = RecetaDiaria(
        fecha = java.time.LocalDate.now(),
        nombre = "Receta del día: Pollo al horno con verduras y arroz integral",
        descripcion = "Disfruta de esta deliciosa receta saludable."
    )
    RecetaItem(receta = recetaEjemplo)
}