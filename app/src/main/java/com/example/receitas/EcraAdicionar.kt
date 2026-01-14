package com.example.receitas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.receitas.ui.theme.GreenBackground
import com.example.receitas.ui.theme.OffWhitePanel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EcraAdicionar(navController: NavController) {
    // 1. Variáveis de Estado (Agora com PREPARAÇÃO)
    var nomeReceita by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf("") }
    var preparacao by remember { mutableStateOf("") } // <--- NOVO

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

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
                .imePadding()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(OffWhitePanel)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AddCircle, null, modifier = Modifier.size(40.dp), tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nova Receita", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Imagem (Placeholder)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Image, "Foto", modifier = Modifier.size(50.dp), tint = Color.Gray)
                    Text("Carregar Foto (Brevemente)", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input NOME
            OutlinedTextField(
                value = nomeReceita,
                onValueChange = { nomeReceita = it },
                label = { Text("Nome da Receita") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input INGREDIENTES
            OutlinedTextField(
                value = ingredientes,
                onValueChange = { ingredientes = it },
                label = { Text("Ingredientes") },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp),
                minLines = 3,
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- NOVO CAMPO: Input PREPARAÇÃO ---
            OutlinedTextField(
                value = preparacao,
                onValueChange = { preparacao = it },
                label = { Text("Modo de Preparação") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Caixa mais alta
                shape = RoundedCornerShape(12.dp),
                minLines = 5, // Começa com 5 linhas visíveis
                maxLines = 10,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão GUARDAR
            Button(
                onClick = {
                    if (nomeReceita.isEmpty() || ingredientes.isEmpty() || preparacao.isEmpty()) {
                        Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                    } else {
                        val receitaParaGuardar = hashMapOf(
                            "titulo" to nomeReceita,
                            "ingredientes" to ingredientes,
                            "preparacao" to preparacao, // <--- Agora enviamos a preparação também!
                            "autor" to "Eu"
                        )

                        db.collection("receitas")
                            .add(receitaParaGuardar)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Receita Guardada!", Toast.LENGTH_LONG).show()
                                navController.popBackStack()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Receita", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}