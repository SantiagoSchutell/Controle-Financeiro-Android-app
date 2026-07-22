package com.example.meucontrolefinaceiro.data.model

import java.util.Date

data class Tranzacao(
        val tranzacaoId : String,
        val valorTranzacao: Double,
        val descricao: String?,
        val tipoTranzacao: String,
        val dataTranzacao: Date
)
