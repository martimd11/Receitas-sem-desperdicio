package com.example.receitas

data class Receita(
    val id: Int,
    val nome: String,
    val ingredientes: String,
    val preparacao: String
)

// A tua lista de receitas
val listaDeReceitas = listOf(
    Receita(
        id = 1,
        nome = "Bolo de bolacha",
        ingredientes = "Inserir ingredientes",
        preparacao = ""
    ),
    Receita(
        id = 2,
        nome = "Caldo Verde",
        ingredientes = "500g de batata\n1 cebola\n2 dentes de alho\n200g de couve galega\n1 chouriço\nAzeite e sal",
        preparacao = "1. Coza as batatas, cebola e alho com o chouriço.\n2. Triture tudo (sem o chouriço).\n3. Junte a couve e deixe cozer.\n4. Sirva com rodelas de chouriço."
    )
)

