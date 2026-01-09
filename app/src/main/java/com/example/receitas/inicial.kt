package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.receitas.ui.theme.GreenBackground
import com.example.receitas.ui.theme.OffWhitePanel

@Composable
fun EcraInicio(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Títulos
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Receitas sem Desperdicio To Go",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Da dispensa para o prato",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
                    color = Color.Black
                )
            }

            // Imagem Central
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(250.dp)
                    .background(OffWhitePanel, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingBasket,
                    contentDescription = "Ilustração",
                    modifier = Modifier.size(100.dp),
                    tint = GreenBackground
                )
            }

            // Botões Grandes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // --- BOTÃO DO MAPA ATUALIZADO ---
                BotaoGrande(
                    texto = "Ofertas locais",
                    icon = Icons.Filled.LocationOn,
                    onClick = { navController.navigate("mapa") } // <--- AGORA VAI PARA O MAPA
                )

                BotaoGrande(
                    texto = "Aproveitar em casa",
                    icon = Icons.Filled.ShoppingBasket,
                    onClick = { navController.navigate("aproveitar") }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BotaoGrande(texto: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(OffWhitePanel, RoundedCornerShape(16.dp))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xFFD32F2F)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = texto, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}