package com.example.receitas

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun EcraMapa(navController: NavController) {
    // Ponto inicial: Ajustado para o centro entre a FruTITA e a ReFood
    val pontoInicial = LatLng(41.6930, -8.8330)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pontoInicial, 15f)
    }

    // ESTADO: Guarda qual a loja que foi clicada (null = nenhuma)
    var lojaSelecionada by remember { mutableStateOf<Loja?>(null) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        // --- MAPA ---
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                lojaSelecionada = null // Clicar fora fecha o cartão
            }
        ) {
            listaDeLojas.forEach { loja ->
                Marker(
                    state = MarkerState(position = loja.coordenadas),
                    title = loja.nome,
                    // Usa a cor definida no Enum (Azul, Verde ou Laranja)
                    icon = BitmapDescriptorFactory.defaultMarker(loja.tipo.corHue),
                    onClick = {
                        lojaSelecionada = loja
                        // Anima a câmara para centrar na loja
                        scope.launch {
                            cameraPositionState.animate(CameraUpdateFactory.newLatLng(loja.coordenadas))
                        }
                        true
                    }
                )
            }
        }

        // --- BOTÃO VOLTAR ---
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 40.dp, start = 16.dp)
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.Black)
        }

        // --- LEGENDA (Canto Superior Direito) ---
        LegendaMapa(modifier = Modifier.align(Alignment.TopEnd).padding(top = 40.dp, end = 16.dp))

        // --- CARTÃO DE DETALHES (ANIMADO) ---
        AnimatedVisibility(
            visible = lojaSelecionada != null,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            lojaSelecionada?.let { loja ->
                CardDetalhesLoja(
                    loja = loja,
                    onClose = { lojaSelecionada = null }
                )
            }
        }
    }
}

// --- COMPONENTE DO CARTÃO DE DETALHES ---
@Composable
fun CardDetalhesLoja(loja: Loja, onClose: () -> Unit) {
    val context = LocalContext.current

    // Cores personalizadas para o texto do Tipo
    val corDoTipo = when(loja.tipo) {
        TipoLoja.SOLIDARIO -> Color(0xFFFF9800) // Laranja
        TipoLoja.LOCAL -> Color(0xFF4CAF50)     // Verde
        else -> Color(0xFF007FFF)               // Azul
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // 1. Cabeçalho
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = loja.nome,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = loja.tipo.tituloLegenda,
                        fontSize = 14.sp,
                        color = corDoTipo,
                        fontWeight = FontWeight.Medium
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Info Principal
            InfoRow(Icons.Default.Schedule, "Horário: ${loja.horario}")
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(Icons.Default.Place, loja.endereco)

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // 3. Descrição
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = corDoTipo, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if(loja.tipo == TipoLoja.SOLIDARIO) "Impacto Social:" else "Detalhes:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = corDoTipo
                    )
                    Text(
                        text = loja.descricao,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Botão GPS
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("google.navigation:q=${loja.coordenadas.latitude},${loja.coordenadas.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Rota no Maps")
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = Color.DarkGray)
    }
}

// --- LEGENDA NOVA (Sem Supermercados) ---
@Composable
fun LegendaMapa(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Legenda", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)

            // Agora temos 3 categorias
            ItemLegenda(Color.Blue, "Mercados e Feiras")
            ItemLegenda(Color.Green, "Comércio Local / Mini")
            ItemLegenda(Color(0xFFFF9800), "Apoio Social / ONG")
        }
    }
}

@Composable
fun ItemLegenda(cor: Color, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).background(cor, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, fontSize = 12.sp, color = Color.DarkGray)
    }
}