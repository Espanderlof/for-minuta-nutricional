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
    val descripcion: String,
    val recomendacionNutricional: String
)

val recetasSemana = listOf(
    RecetaDiaria(
        LocalDate.now(),
        "Pollo al horno con verduras y arroz integral",
        "Disfruta de esta deliciosa receta saludable.",
        "Alto en proteínas y fibra. Proporciona energía de liberación lenta."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(1),
        "Salmón a la plancha con ensalada mixta",
        "Una comida ligera y nutritiva.",
        "Rico en ácidos grasos omega-3. Ayuda a la salud cardiovascular."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(2),
        "Pasta integral con salsa de tomate y albahaca",
        "Un plato vegetariano lleno de sabor.",
        "Buena fuente de carbohidratos complejos y licopeno."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(3),
        "Lentejas con verduras y arroz",
        "Una combinación perfecta de proteínas vegetales.",
        "Alto contenido en hierro y fibra. Excelente para la digestión."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(4),
        "Tortilla de espinacas y champiñones",
        "Una opción saludable y sabrosa.",
        "Rica en folatos y vitamina D. Beneficioso para el sistema inmunológico."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(5),
        "Ensalada César con pollo a la parrilla",
        "Una ensalada clásica con un toque proteico.",
        "Baja en calorías y alta en proteínas. Ideal para control de peso."
    ),
    RecetaDiaria(
        LocalDate.now().plusDays(6),
        "Pescado al vapor con brócoli y quinoa",
        "Un plato equilibrado y nutritivo.",
        "Excelente fuente de proteínas magras y antioxidantes."
    )
)

@Composable
fun MinutaNutricionalScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Minuta Nutricional Semanal",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(recetasSemana) { receta ->
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recomendación nutricional: ${receta.recomendacionNutricional}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
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
    val recetaEjemplo = recetasSemana[0]
    RecetaItem(receta = recetaEjemplo)
}