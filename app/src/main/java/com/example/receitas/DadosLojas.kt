package com.example.receitas

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

data class Loja(
    val id: Int,
    val nome: String,
    val coordenadas: LatLng,
    val descricao: String,
    val tipo: TipoLoja,
    val endereco: String,
    val horario: String
)

// Tipos de loja (Legenda simplificada)
enum class TipoLoja(val corHue: Float, val tituloLegenda: String) {
    MERCADO(BitmapDescriptorFactory.HUE_AZURE, "Mercados e Feiras"),            // Azul
    LOCAL(BitmapDescriptorFactory.HUE_GREEN, "Comércio Local / Mini-mercados"), // Verde
    SOLIDARIO(BitmapDescriptorFactory.HUE_ORANGE, "Apoio Social / ONG")         // Laranja
}

// --- LISTA DE LOJAS (VIANA DO CASTELO) ---
val listaDeLojas = listOf(

    // --- COMÉRCIO LOCAL / MINI-MERCADOS ---

    // 1. Spar (NOVO)
    Loja(
        id = 1,
        nome = "Spar",
        coordenadas = LatLng(41.695943055910725, -8.84025152020911),
        descricao = "Mini-mercado de conveniência com essenciais do dia-a-dia.",
        tipo = TipoLoja.LOCAL,
        endereco = "R. Cidade do Recife 5, Viana do Castelo",
        horario = "08:30 - 20:00"
    ),

    // 2. Supermercado Económico (NOVO)
    Loja(
        id = 2,
        nome = "Supermercado Económico",
        coordenadas = LatLng(41.69119897535489, -8.832005739236696),
        descricao = "Mercearia de bairro tradicional com preços acessíveis.",
        tipo = TipoLoja.LOCAL,
        endereco = "Beco do Penedo, Viana do Castelo",
        horario = "09:00 - 19:00"
    ),

    // 3. FruTITA
    Loja(
        id = 3,
        nome = "FruTITA",
        coordenadas = LatLng(41.69155916668279, -8.832184744781587),
        descricao = "Frutaria de bairro com atendimento personalizado.",
        tipo = TipoLoja.LOCAL,
        endereco = "R. de Olivença 117, Viana do Castelo",
        horario = "08:00 - 19:30"
    ),

    // 4. Frutolima (Monserrate)
    Loja(
        id = 4,
        nome = "Frutolima",
        coordenadas = LatLng(41.70593290917259, -8.807498627665868),
        descricao = "Fruta fresca e legumes da época.",
        tipo = TipoLoja.LOCAL,
        endereco = "Rua de Monserrate, Viana do Castelo",
        horario = "09:00 - 19:00"
    ),

    // 5. Frutolima (Papanata)
    Loja(
        id = 5,
        nome = "Frutolima",
        coordenadas = LatLng(41.70305739253657, -8.824239590449164),
        descricao = "Produtos locais e sustentáveis.",
        tipo = TipoLoja.LOCAL,
        endereco = "Estrada da Papanata, Viana do Castelo",
        horario = "09:00 - 19:00"
    ),

    // --- MERCADOS E FEIRAS ---

    // 6. Feira Semanal
    Loja(
        id = 6,
        nome = "Feira Semanal",
        coordenadas = LatLng(41.690842815718455, -8.837473068321033),
        descricao = "Ocorre todas as sextas-feiras. O melhor sítio para frescos baratos.",
        tipo = TipoLoja.MERCADO,
        endereco = "Av. Campo do Castelo 24, Viana do Castelo",
        horario = "Sextas: 07:00 - 18:00"
    ),

    // 7. Mercado Municipal
    Loja(
        id = 7,
        nome = "Mercado Municipal",
        coordenadas = LatLng(41.69849266887026, -8.826052201328888),
        descricao = "O coração dos frescos em Viana.",
        tipo = TipoLoja.MERCADO,
        endereco = "Av. Campo do Castelo, Viana do Castelo",
        horario = "07:00 - 13:00 (Fecha Dom.)"
    ),

    // 8. Mercado de Verduras (S. Domingos)
    Loja(
        id = 8,
        nome = "Bancas de S. Domingos",
        coordenadas = LatLng(41.69963045438367, -8.825019020248856),
        descricao = "Bancas tradicionais de rua.",
        tipo = TipoLoja.MERCADO,
        endereco = "Largo de São Domingos",
        horario = "07:30 - 12:30 (Sex. apenas)"
    ),

    // --- APOIO SOCIAL ---

    // 9. ReFood Viana do Castelo
    Loja(
        id = 9,
        nome = "ReFood Viana do Castelo",
        coordenadas = LatLng(41.6940558080273, -8.834357944181193),
        descricao = "Resgate de alimentos e apoio à comunidade.",
        tipo = TipoLoja.SOLIDARIO,
        endereco = "R. Gen. Humberto Delgado 262",
        horario = "18:00 - 21:00 (Variável)"
    )
)