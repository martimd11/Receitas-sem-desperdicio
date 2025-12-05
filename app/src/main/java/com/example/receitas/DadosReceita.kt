package com.example.receitas

data class Receita(
    val id: Int,
    val nome: String,
    val ingredientes: String,
    val preparacao: String
)

// A lista de receitas (Base de Dados Simulada)
val listaDeReceitas = listOf(
    Receita(
        id = 1,
        nome = "Bolo de bolacha",
        ingredientes = "Bolachas Maria\nCafé forte\nManteiga\nAçúcar\nGema de ovo",
        preparacao = "1. Faça o café e deixe arrefecer.\n2. Bata a manteiga com o açúcar e a gema.\n3. Molhe as bolachas no café.\n4. Alterne camadas de bolacha e creme.\n5. Leve ao frigorífico."
    ),
    Receita(
        id = 2,
        nome = "Caldo Verde",
        ingredientes = "500g de batata\n1 cebola\n2 dentes de alho\n200g de couve galega\n1 chouriço\nAzeite e sal",
        preparacao = "1. Coza as batatas, cebola e alho com o chouriço.\n2. Triture tudo (sem o chouriço).\n3. Junte a couve cortada e deixe cozer.\n4. Sirva com rodelas de chouriço e um fio de azeite."
    )
)

