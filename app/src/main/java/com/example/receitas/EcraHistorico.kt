package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.firebase.firestore.FirebaseFirestore

// MUDANÇA AQUI: Mudei o nome para não haver conflito com o teu ficheiro antigo
data class ReceitaFirebase(
    val id: String,
    val titulo: String
)

@Composable
fun EcraHistorico(navController: NavController) {
    // Agora usamos a "ReceitaFirebase"
    val listaReceitas = remember { mutableStateListOf<ReceitaFirebase>() }
    var aCarregar by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("receitas")
            .get()
            .addOnSuccessListener { documentos ->
                listaReceitas.clear()
                for (documento in documentos) {
                    val id = documento.id
                    val titulo = documento.getString("titulo") ?: "Sem Título"
                    // Cria o objeto com o novo nome
                    listaReceitas.add(ReceitaFirebase(id, titulo))
                }
                aCarregar = false
            }
            .addOnFailureListener {
                aCarregar = false
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBackground)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp)
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.Black)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(OffWhitePanel)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.History, null, modifier = Modifier.size(70.dp), tint = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Histórico de receitas", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))
            Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.width(200.dp).padding(top = 8.dp))

            Spacer(modifier = Modifier.height(50.dp))

            if (aCarregar) {
                CircularProgressIndicator(color = Color.Black)
                Text("A carregar receitas...", modifier = Modifier.padding(top = 16.dp))
            } else if (listaReceitas.isEmpty()) {
                Text("Ainda não tens receitas guardadas.", color = Color.Gray)
            } else {
                listaReceitas.forEach { receita ->
                    ItemHistorico(nome = receita.titulo) {
                        navController.navigate("detalhes/${receita.id}")
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@Composable
fun ItemHistorico(nome: String, aoClicar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { aoClicar() },
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