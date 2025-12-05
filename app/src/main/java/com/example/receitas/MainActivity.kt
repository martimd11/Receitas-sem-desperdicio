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

    // Lógica para esconder a barra de navegação no ecrã de Login
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute != "login" // Se for "login", a barra desaparece

    Scaffold(
        bottomBar = {
            if (showBottomBar) { // Só desenha a barra se não estivermos no login
                NavigationBar(
                    containerColor = GreenBackground,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    val currentDestination = navBackStackEntry?.destination

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
        // --- NAVHOST: Onde definimos todas as rotas da aplicação ---
        NavHost(
            navController = navController,
            startDestination = "login", // <--- O PONTO DE PARTIDA É O LOGIN
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Ecrã de Login
            composable("login") {
                // Certifica-te que o teu ficheiro EcraLogin tem a função chamada "EcraLogin"
                EcraLogin(navController)
            }

            // 2. Ecrã Início
            composable("inicio") {
                EcraInicio(navController)
            }

            // 3. Ecrã Aproveitar (Pesquisa)
            composable("aproveitar") {
                EcraAproveitar(navController)
            }

            // 4. Ecrã Histórico
            composable("historico") {
                EcraHistorico(navController)
            }

            // 5. Ecrã Adicionar
            composable("adicionar") {
                EcraAdicionar(navController)
            }

            // 6. Ecrã de Detalhes (Inteligente - Recebe o ID da receita)
            composable(
                route = "detalhes/{receitaId}",
                arguments = listOf(navArgument("receitaId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Pega o número do ID enviado e abre o ecrã com a receita certa
                val id = backStackEntry.arguments?.getInt("receitaId") ?: 1
                EcraDetalhes(navController, id)
            }
        }
    }
}