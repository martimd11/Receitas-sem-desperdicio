package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.receitas.ui.theme.BrownBasket

@Composable
fun EcraAproveitar(navController: NavController) {
    var textoPesquisa by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBackground)
    ) {
        // --- CABEÇALHO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botão Voltar
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Black)
            }

            // Botão Histórico (Corrigido)
            IconButton(
                onClick = { navController.navigate("historico") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Histórico", tint = Color.Black)
            }
        } // <--- A CHAVETA QUE FALTAVA ESTAVA AQUI!

        // --- PAINEL CENTRAL BRANCO ---
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(70.dp).background(Color.Black, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, "Adicionar", tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                    Text("Adicionar\nReceitas", style = TextStyle(fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)
                }
                Icon(Icons.Default.Home, "Cesto", modifier = Modifier.size(90.dp), tint = BrownBasket)
            }
            Spacer(modifier = Modifier.height(50.dp))

            // Aqui chama a função da barra de pesquisa
            BarraDePesquisa(texto = textoPesquisa, aoMudarTexto = { textoPesquisa = it })
        }
    }
}

// --- OUTRAS FUNÇÕES (Fora do EcraAproveitar) ---

@Composable
fun BotaoQuadradoPequeno(icon: ImageVector) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(48.dp).background(Color.White, RoundedCornerShape(12.dp))) {
        Icon(icon, null, tint = Color.Black, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun BarraDePesquisa(texto: String, aoMudarTexto: (String) -> Unit) {
    BasicTextField(
        value = texto, onValueChange = aoMudarTexto,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.White, RoundedCornerShape(50)).border(1.5.dp, Color.Black, RoundedCornerShape(50)).padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, null, tint = Color.Black, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.width(1.dp).height(28.dp).background(Color.Black))
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (texto.isEmpty()) Text("ex: banana, farinha", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 18.sp))
                    innerTextField()
                }
            }
        }
    )
}