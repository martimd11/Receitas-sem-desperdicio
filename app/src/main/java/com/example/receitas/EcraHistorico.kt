package com.example.receitas

import androidx.compose.foundation.background
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
        // Botão de Voltar (Canto Superior Esquerdo)
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
            // Ícone do Relógio
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título
            Text(
                text = "Histórico de receitas",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )

            // Linha decorativa
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.width(200.dp).padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Itens da Lista
            ItemHistorico(nome = "Bolo de bolacha")
            Spacer(modifier = Modifier.height(30.dp))
            ItemHistorico(nome = "Caldo Verde")
        }
    }
}

@Composable
fun ItemHistorico(nome: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = nome,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            // Sublinhado do item
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.width(180.dp).padding(top = 4.dp)
            )
        }

        // Seta
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Ir",
            modifier = Modifier.size(40.dp),
            tint = Color.Black
        )
    }
}