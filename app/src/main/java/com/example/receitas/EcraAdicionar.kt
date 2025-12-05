package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.receitas.ui.theme.GreenBackground
import com.example.receitas.ui.theme.OffWhitePanel

@Composable
fun EcraAdicionar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBackground)
    ) {
        // Botão Voltar
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp)
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.Black)
        }

        // Painel Central
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(OffWhitePanel)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Adicionar Nova Receita",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Placeholder para o formulário
            Text("Formulário para adicionar receitas aparecerá aqui...")
        }
    }
}