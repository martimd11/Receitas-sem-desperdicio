package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.receitas.ui.theme.GreenBackground
import com.example.receitas.ui.theme.OffWhitePanel
import com.example.receitas.ui.theme.BrownBasket
import com.google.firebase.firestore.FirebaseFirestore

// Modelo de dados para a Base de Dados
data class ReceitaBD(
    val id: String,
    val nome: String,
    val ingredientes: String
)

@Composable
fun EcraAproveitar(navController: NavController) {
    var textoPesquisa by remember { mutableStateOf("") }

    // Lista vazia que vai ser enchida pela Base de Dados
    var listaDaBaseDeDados by remember { mutableStateOf<List<ReceitaBD>>(emptyList()) }

    // Ligar à Firebase e sacar os dados
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("receitas")
            .addSnapshotListener { result, e ->
                if (e != null) return@addSnapshotListener

                if (result != null) {
                    val receitas = result.documents.map { doc ->
                        ReceitaBD(
                            id = doc.id,
                            nome = doc.getString("titulo") ?: "Sem nome",
                            ingredientes = doc.getString("ingredientes") ?: ""
                        )
                    }
                    listaDaBaseDeDados = receitas
                }
            }
    }

    // FILTRO: Filtra a lista que veio da Internet
    val receitasFiltradas = listaDaBaseDeDados.filter { receita ->
        receita.nome.contains(textoPesquisa, ignoreCase = true) ||
                receita.ingredientes.contains(textoPesquisa, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBackground)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("historico") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.History, contentDescription = "Histórico", tint = Color.Black)
            }
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
            // Botões do Topo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate("adicionar") }
                ) {
                    Box(modifier = Modifier.size(70.dp).background(Color.Black, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, "Adicionar", tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                    Text("Adicionar\nReceitas", style = TextStyle(fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)
                }

                Icon(Icons.Default.Home, "Cesto", modifier = Modifier.size(90.dp), tint = BrownBasket)
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Barra de Pesquisa
            BarraDePesquisa(texto = textoPesquisa, aoMudarTexto = { textoPesquisa = it })

            Spacer(modifier = Modifier.height(20.dp))

            // LISTA DA BASE DE DADOS
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(receitasFiltradas) { receita ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .clickable {
                                // Passa o ID real da Firebase
                                navController.navigate("detalhes/${receita.id}")
                            }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = receita.nome,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        )
                    }
                }
            }
        }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White, RoundedCornerShape(50))
                    .border(1.5.dp, Color.Black, RoundedCornerShape(50))
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, null, tint = Color.Black, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color.Black))
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (texto.isEmpty()) {
                        Text("pesquisar por receitas...", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 18.sp))
                    }
                    innerTextField()
                }
            }
        }
    )
}