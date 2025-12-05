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
fun EcraDetalhes(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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

        // --- Título Principal ---
        Text(
            text = "Título da Receita",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32) // Verde
        )

        Divider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 2.dp,
            color = Color.LightGray
        )

        // --- Secção Ingredientes (SÓ O TÍTULO) ---
        Text(
            text = "Ingredientes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Espaço em branco onde os ingredientes iriam aparecer
        Spacer(modifier = Modifier.height(40.dp))

        // --- Secção Preparação (SÓ O TÍTULO) ---
        Text(
            text = "Modo de Preparação",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Espaço em branco onde a preparação iria aparecer
        Spacer(modifier = Modifier.height(40.dp))
    }
}

