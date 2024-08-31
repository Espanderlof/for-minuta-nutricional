package com.example.minutanutricional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minutanutricional.ui.theme.MinutaNutricionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CuentasManager.agregarCuentasDePrueba()
        setContent {
            MinutaNutricionalTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onNavigateToRecuperarContrasena = { navController.navigate("recuperar_contrasena") },
                            onNavigateToCrearCuenta = { navController.navigate("crear_cuenta") },
                            onNavigateToMinutaNutricional = { navController.navigate("minuta_nutricional") }
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
                        MinutaNutricionalScreen()
                    }
                }
            }
        }
    }
}