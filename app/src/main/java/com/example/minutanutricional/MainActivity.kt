package com.example.minutanutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.minutanutricional.ui.theme.MinutaNutricionalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(this)
        CuentasManager.agregarCuentasDePrueba()

        setContent {
            MinutaNutricionalTheme {
                MinutaNutricionalApp(userPreferences)
            }
        }
    }
}

// S7
@Composable
fun MinutaNutricionalApp(userPreferences: UserPreferences) {
    val navController = rememberNavController()
    val isLoggedIn by userPreferences.isLoggedIn.collectAsState(initial = false)

    NavHost(navController = navController, startDestination = if (isLoggedIn) "minuta_nutricional" else "login") {
        composable("login") {
            LoginScreen(
                onNavigateToRecuperarContrasena = { navController.navigate("recuperar_contrasena") },
                onNavigateToCrearCuenta = { navController.navigate("crear_cuenta") },
                onNavigateToMinutaNutricional = { email ->
                    navController.navigate("minuta_nutricional") {
                        popUpTo("login") { inclusive = true }
                    }
                    // Guardar el estado de inicio de sesión
                    (navController.context as? ComponentActivity)?.lifecycleScope?.launch {
                        userPreferences.setLoggedIn(true)
                        userPreferences.setUserEmail(email)
                    }
                }
            )
        }
        composable("recuperar_contrasena") {
            RecuperarContrasenaScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable("crear_cuenta") {
            CrearCuentaScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable("minuta_nutricional") {
            MinutaNutricionalScreen(
                navController = navController,
                onLogout = {
                    // Cerrar sesión
                    (navController.context as? ComponentActivity)?.lifecycleScope?.launch {
                        userPreferences.clearUserData()
                    }
                    navController.navigate("login") {
                        popUpTo("minuta_nutricional") { inclusive = true }
                    }
                }
            )
        }
        composable(
            "receta_detalle/{recetaId}",
            arguments = listOf(navArgument("recetaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getInt("recetaId") ?: return@composable
            RecetaDetalleFragment(recetaId, navController)
        }
    }
}