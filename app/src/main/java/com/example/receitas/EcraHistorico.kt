package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Divider
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
fun EcraHistorico(navController: NavController) {
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
            Icon(Icons.Default.History, null, modifier = Modifier.size(70.dp), tint = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Histórico de receitas", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.width(200.dp).padding(top = 8.dp))

            Spacer(modifier = Modifier.height(50.dp))

            // --- LISTA DE RECEITAS ---
            // ID 1 = Bolo de Bolacha
            ItemHistorico(nome = "Bolo de bolacha") {
                navController.navigate("detalhes/1")
            }
            Spacer(modifier = Modifier.height(30.dp))

            // ID 2 = Caldo Verde
            ItemHistorico(nome = "Caldo Verde") {
                navController.navigate("detalhes/2")
            }
        }
    }
}

@Composable
fun ItemHistorico(nome: String, aoClicar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { aoClicar() }, // Torna a linha clicável
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = nome, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.width(180.dp).padding(top = 4.dp))
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, "Ir", modifier = Modifier.size(40.dp), tint = Color.Black)
    }
}