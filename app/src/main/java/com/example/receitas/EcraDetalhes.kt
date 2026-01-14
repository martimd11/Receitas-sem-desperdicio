package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EcraDetalhes(navController: NavController, receitaId: String?) { // Agora aceita String (e pode ser nulo)

    // Variáveis para guardar os dados que vêm da internet
    var titulo by remember { mutableStateOf("A carregar...") }
    var ingredientes by remember { mutableStateOf("") }
    var preparacao by remember { mutableStateOf("") }
    var aCarregar by remember { mutableStateOf(true) }

    // --- IR BUSCAR AO FIREBASE ---
    LaunchedEffect(receitaId) {
        if (receitaId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("receitas").document(receitaId).get()
                .addOnSuccessListener { documento ->
                    if (documento != null && documento.exists()) {
                        titulo = documento.getString("titulo") ?: "Sem Título"
                        ingredientes = documento.getString("ingredientes") ?: "Sem Ingredientes"
                        // Nota: No ecrã adicionar ainda não pusemos "preparacao", por isso vai aparecer o texto padrão
                        preparacao = documento.getString("preparacao") ?: "Informação de preparação não disponível."
                    } else {
                        titulo = "Receita não encontrada"
                    }
                    aCarregar = false
                }
                .addOnFailureListener {
                    titulo = "Erro de ligação"
                    aCarregar = false
                }
        }
    }

    // --- O ECRÃ ---
    if (aCarregar) {
        // Mostra um círculo a rodar enquanto carrega
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF2E7D32))
        }
    } else {
        // Mostra o conteúdo quando estiver pronto
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

            // --- Título da Receita (Verde) ---
            Text(
                text = titulo,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
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
                text = ingredientes,
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
                text = preparacao,
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
    }
}
