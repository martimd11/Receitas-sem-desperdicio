package com.example.receitas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun EcraMapa(navController: NavController) {
    // Coordenadas iniciais (Viana do Castelo)
    val viana = LatLng(41.6932, -8.8329)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viana, 12f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // O Mapa Google
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Exemplo de um Marcador (Mercado)
            Marker(
                state = MarkerState(position = viana),
                title = "Mercado Local",
                snippet = "Ofertas de frescos hoje!"
            )
        }

        // Botão de Voltar (No topo esquerdo)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 40.dp, start = 16.dp) // Margem para não ficar colado ao topo
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }
    }
}