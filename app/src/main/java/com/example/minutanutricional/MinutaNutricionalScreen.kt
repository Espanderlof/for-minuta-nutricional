package com.example.minutanutricional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

data class RecetaDiaria(
    val id: Int,
    val fecha: LocalDate,
    val nombre: String,
    val descripcion: String,
    val recomendacionNutricional: String,
    val ingredientes: List<String>,
    val preparacion: String
)

val recetasSemana = listOf(
    RecetaDiaria(
        id = 1,
        fecha = LocalDate.now(),
        nombre = "Pollo al horno con verduras y arroz integral",
        descripcion = "Una combinación saludable y deliciosa de proteínas magras y carbohidratos complejos.",
        recomendacionNutricional = "Alto en proteínas y fibra. Proporciona energía de liberación lenta.",
        ingredientes = listOf(
            "2 pechugas de pollo",
            "1 taza de arroz integral",
            "2 zanahorias",
            "1 pimiento rojo",
            "1 cebolla",
            "2 cucharadas de aceite de oliva",
            "Sal y pimienta al gusto",
            "1 cucharadita de romero seco"
        ),
        preparacion = """
            1. Precalentar el horno a 200°C.
            2. Lavar y cortar las verduras en trozos medianos.
            3. Colocar el pollo y las verduras en una bandeja para horno.
            4. Rociar con aceite de oliva y sazonar con sal, pimienta y romero.
            5. Hornear por 25-30 minutos o hasta que el pollo esté cocido.
            6. Mientras tanto, cocinar el arroz integral según las instrucciones del paquete.
            7. Servir el pollo y las verduras sobre el arroz.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 2,
        fecha = LocalDate.now().plusDays(1),
        nombre = "Salmón a la plancha con ensalada mixta",
        descripcion = "Una comida ligera y nutritiva, rica en ácidos grasos omega-3.",
        recomendacionNutricional = "Rico en ácidos grasos omega-3. Ayuda a la salud cardiovascular.",
        ingredientes = listOf(
            "2 filetes de salmón",
            "1 limón",
            "2 tazas de mezcla de lechugas",
            "1 tomate",
            "1/2 pepino",
            "1/4 de cebolla roja",
            "2 cucharadas de aceite de oliva",
            "1 cucharada de vinagre balsámico",
            "Sal y pimienta al gusto"
        ),
        preparacion = """
            1. Sazonar los filetes de salmón con sal, pimienta y jugo de limón.
            2. Calentar una sartén a fuego medio-alto y cocinar el salmón por 4-5 minutos por cada lado.
            3. Mientras tanto, lavar y cortar las verduras para la ensalada.
            4. En un bol grande, mezclar las verduras cortadas.
            5. Preparar el aderezo mezclando aceite de oliva, vinagre balsámico, sal y pimienta.
            6. Servir el salmón con la ensalada al lado y rociar con el aderezo.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 3,
        fecha = LocalDate.now().plusDays(2),
        nombre = "Pasta integral con salsa de tomate y albahaca",
        descripcion = "Un plato vegetariano lleno de sabor y nutrientes.",
        recomendacionNutricional = "Buena fuente de carbohidratos complejos y licopeno.",
        ingredientes = listOf(
            "300g de pasta integral",
            "400g de tomates pelados enlatados",
            "1 cebolla",
            "2 dientes de ajo",
            "1 manojo de albahaca fresca",
            "2 cucharadas de aceite de oliva",
            "Sal y pimienta al gusto",
            "Queso parmesano rallado (opcional)"
        ),
        preparacion = """
            1. Cocinar la pasta integral según las instrucciones del paquete.
            2. Mientras tanto, picar finamente la cebolla y el ajo.
            3. En una sartén grande, calentar el aceite de oliva y sofreír la cebolla y el ajo hasta que estén dorados.
            4. Añadir los tomates pelados y cocinar a fuego medio por 15 minutos.
            5. Agregar la albahaca picada y sazonar con sal y pimienta.
            6. Escurrir la pasta y mezclarla con la salsa de tomate.
            7. Servir y espolvorear con queso parmesano si se desea.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 4,
        fecha = LocalDate.now().plusDays(3),
        nombre = "Lentejas con verduras y arroz",
        descripcion = "Una combinación perfecta de proteínas vegetales y fibra.",
        recomendacionNutricional = "Alto contenido en hierro y fibra. Excelente para la digestión.",
        ingredientes = listOf(
            "1 taza de lentejas",
            "1/2 taza de arroz integral",
            "1 zanahoria",
            "1 cebolla",
            "1 pimiento verde",
            "2 dientes de ajo",
            "1 hoja de laurel",
            "2 cucharadas de aceite de oliva",
            "Sal y comino al gusto"
        ),
        preparacion = """
            1. Lavar las lentejas y ponerlas a cocinar en agua con la hoja de laurel por 20 minutos.
            2. En una olla aparte, cocinar el arroz integral según las instrucciones del paquete.
            3. Mientras tanto, picar todas las verduras.
            4. En una sartén grande, calentar el aceite y sofreír la cebolla, el ajo y las verduras.
            5. Añadir las lentejas cocidas a las verduras y sazonar con sal y comino.
            6. Cocinar todo junto por 10 minutos más.
            7. Servir las lentejas con verduras sobre el arroz integral.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 5,
        fecha = LocalDate.now().plusDays(4),
        nombre = "Tortilla de espinacas y champiñones",
        descripcion = "Una opción saludable y sabrosa, rica en nutrientes.",
        recomendacionNutricional = "Rica en folatos y vitamina D. Beneficioso para el sistema inmunológico.",
        ingredientes = listOf(
            "6 huevos",
            "200g de espinacas frescas",
            "150g de champiñones",
            "1 cebolla pequeña",
            "2 cucharadas de aceite de oliva",
            "Sal y pimienta al gusto",
            "30g de queso rallado (opcional)"
        ),
        preparacion = """
            1. Lavar y picar las espinacas. Limpiar y cortar los champiñones en rodajas.
            2. En una sartén grande, calentar 1 cucharada de aceite y sofreír la cebolla picada.
            3. Añadir los champiñones y cocinar hasta que suelten su agua.
            4. Agregar las espinacas y cocinar hasta que se marchiten.
            5. En un bol grande, batir los huevos y sazonar con sal y pimienta.
            6. Añadir las verduras cocinadas a los huevos batidos y mezclar bien.
            7. En la misma sartén, calentar el resto del aceite y verter la mezcla de huevo y verduras.
            8. Cocinar a fuego medio-bajo hasta que esté casi cuajada.
            9. Si se desea, espolvorear el queso rallado por encima.
            10. Dar la vuelta a la tortilla y cocinar por el otro lado hasta que esté dorada.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 6,
        fecha = LocalDate.now().plusDays(5),
        nombre = "Ensalada César con pollo a la parrilla",
        descripcion = "Una ensalada clásica con un toque proteico, perfecta para una comida ligera.",
        recomendacionNutricional = "Baja en calorías y alta en proteínas. Ideal para control de peso.",
        ingredientes = listOf(
            "2 pechugas de pollo",
            "1 lechuga romana",
            "50g de queso parmesano en láminas",
            "2 rebanadas de pan integral",
            "2 cucharadas de aceite de oliva",
            "1 diente de ajo",
            "Jugo de 1 limón",
            "1 cucharada de mostaza de Dijon",
            "1 anchoa (opcional)",
            "Sal y pimienta al gusto"
        ),
        preparacion = """
            1. Sazonar las pechugas de pollo con sal y pimienta y cocinarlas a la parrilla.
            2. Mientras tanto, lavar y cortar la lechuga en trozos.
            3. Cortar el pan en cubos y tostarlos en una sartén con un poco de aceite y ajo picado para hacer crutones.
            4. Para el aderezo, mezclar en una licuadora: aceite de oliva, jugo de limón, mostaza, anchoa (si se usa), sal y pimienta.
            5. En un bol grande, mezclar la lechuga con el aderezo.
            6. Cortar el pollo en tiras.
            7. Servir la ensalada, colocar el pollo encima, añadir los crutones y espolvorear con queso parmesano.
        """.trimIndent()
    ),
    RecetaDiaria(
        id = 7,
        fecha = LocalDate.now().plusDays(6),
        nombre = "Pescado al vapor con brócoli y quinoa",
        descripcion = "Un plato equilibrado y nutritivo, perfecto para una cena ligera.",
        recomendacionNutricional = "Excelente fuente de proteínas magras y antioxidantes.",
        ingredientes = listOf(
            "2 filetes de pescado blanco (como merluza o bacalao)",
            "1 taza de quinoa",
            "1 cabeza de brócoli",
            "1 limón",
            "2 cucharadas de aceite de oliva",
            "1 diente de ajo",
            "Perejil fresco",
            "Sal y pimienta al gusto"
        ),
        preparacion = """
            1. Enjuagar la quinoa y cocinarla según las instrucciones del paquete.
            2. Cortar el brócoli en floretes y cocinar al vapor por 5-7 minutos hasta que esté tierno pero crujiente.
            3. Sazonar los filetes de pescado con sal, pimienta y jugo de limón.
            4. Cocinar el pescado al vapor por unos 8-10 minutos o hasta que esté opaco y se desmenuce fácilmente.
            5. Mientras tanto, picar el ajo y el perejil finamente.
            6. En una sartén pequeña, calentar el aceite de oliva y sofreír el ajo por un minuto.
            7. Añadir el perejil picado al aceite y retirar del fuego.
            8. Servir el pescado sobre la quinoa, acompañado del brócoli.
            9. Rociar el aceite de ajo y perejil por encima del pescado y las verduras.
        """.trimIndent()
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaNutricionalScreen(navController: NavController, onLogout: () -> Unit) {
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
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(recetasSemana) { receta ->
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

@Composable
fun RecetaItem(receta: RecetaDiaria, onClick: () -> Unit) {
    val localeES = Locale("es", "ES")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
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
    val navController = rememberNavController()
    MinutaNutricionalScreen(navController, onLogout = {})
}

@Preview(showBackground = true)
@Composable
fun RecetaItemPreview() {
    val recetaEjemplo = recetasSemana[0]
    RecetaItem(receta = recetaEjemplo, onClick = {})
}