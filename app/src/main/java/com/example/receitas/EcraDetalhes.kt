package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EcraDetalhes(navController: NavController, receitaId: Int) {
    // Procura a receita na lista pelo ID. Se não encontrar, usa uma vazia.
    val receita = listaDeReceitas.find { it.id == receitaId } ?: listaDeReceitas[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo Branco como na imagem
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Botão de Voltar ---
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Título da Receita (Verde) ---
        Text(
            text = receita.nome,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32) // Verde Escuro
        )

        Divider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        // --- Secção Ingredientes ---
        Text(
            text = "Ingredientes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = receita.ingredientes,
            fontSize = 18.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(30.dp))

        // --- Secção Preparação ---
        Text(
            text = "Modo de Preparação",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = receita.preparacao,
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}
