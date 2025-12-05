package com.example.receitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.receitas.ui.theme.GreenBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoPrincipal()
        }
    }
}

@Composable
fun NavegacaoPrincipal() {
    val navController = rememberNavController()

    // Verifica a rota atual para decidir se mostra a barra de baixo ou não
    // (Geralmente não queremos a barra de navegação no ecrã de Login)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    Scaffold(
        bottomBar = {
            // Só mostramos a barra se NÃO estivermos no login
            if (currentRoute != "login") {
                NavigationBar(
                    containerColor = GreenBackground,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    // Item 1: Início
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Início") },
                        selected = currentDestination?.hierarchy?.any { it.route == "inicio" } == true,
                        onClick = {
                            navController.navigate("inicio") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    // Item 2: Pesquisar / Aproveitar
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        label = { Text("Pesquisar") },
                        selected = currentDestination?.hierarchy?.any { it.route == "aproveitar" } == true,
                        onClick = {
                            navController.navigate("aproveitar") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // NAVHOST COM TODAS AS ROTAS
        NavHost(
            navController = navController,
            startDestination = "login", // <--- MUDADO: Começa no login
            modifier = Modifier.padding(innerPadding)
        ) {
            // Rota do Login (certifica-te que o nome da função no outro ficheiro é Login)
            composable("login") {
                Login(navController)
            }

            composable("inicio") { EcraInicio(navController) }
            composable("aproveitar") { EcraAproveitar(navController) }
            composable("historico") { EcraHistorico(navController) }
            composable("adicionar") { EcraAdicionar(navController) }
        }
    }
}