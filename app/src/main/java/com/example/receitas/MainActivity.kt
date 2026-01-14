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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

    // Lógica para esconder a barra de navegação no Login e no Mapa
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    // Esconder a barra no Login E no Mapa
    val showBottomBar = currentRoute != "login" && currentRoute != "mapa"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = GreenBackground,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    val currentDestination = navBackStackEntry?.destination

                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, null) },
                        label = { Text("Início") },
                        selected = currentDestination?.hierarchy?.any { it.route == "inicio" } == true,
                        onClick = {
                            navController.navigate("inicio") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Search, null) },
                        label = { Text("Pesquisar") },
                        selected = currentDestination?.hierarchy?.any { it.route == "aproveitar" } == true,
                        onClick = {
                            navController.navigate("aproveitar") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "login") {

            // ADICIONA ESTA LINHA AQUI:
            composable("registo") { EcraRegisto(navController) }

            composable("login") { EcraLogin(navController) }
            composable("inicio") { EcraInicio(navController) }
            composable("aproveitar") { EcraAproveitar(navController) }
            composable("historico") { EcraHistorico(navController) }
            composable("adicionar") { EcraAdicionar(navController) }

            // --- NOVA ROTA DO MAPA ---
            composable("mapa") { EcraMapa(navController) }

            // Detalhes da Receita
            composable(
                route = "detalhes/{receitaId}",
                arguments = listOf(navArgument("receitaId") { type = NavType.StringType }) // MUDANÇA 1: StringType
            ) { backStackEntry ->
                // MUDANÇA 2: getString em vez de getInt
                val id = backStackEntry.arguments?.getString("receitaId")

                // Agora passamos o texto (String) para o ecrã
                EcraDetalhes(navController, receitaId = id)
            }
        }
    }
}