// com.example.meucontrolefinaceiro.Data/CoinMarketCapResponse.kt
package com.example.meucontrolefinaceiro.Data

import com.google.gson.annotations.SerializedName

// Classe principal para a resposta da API
data class CoinMarketCapResponse(
    @SerializedName("status") val status: Status,
    // AQUI ESTÁ A MUDANÇA: Remova 'List<'
    @SerializedName("data") val data: Map<String, CryptoData> // Agora mapeia a chave (símbolo) para um objeto CryptoData
)

// Status da requisição
data class Status(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("elapsed") val elapsed: Int,
    @SerializedName("credit_count") val creditCount: Int
)

// Dados da criptomoeda (BTC neste caso)
data class CryptoData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("quote") val quote: Map<String, QuoteData>
)

// Dados da cotação (preço, volume, etc.)
data class QuoteData(
    @SerializedName("price") val price: Double,
    @SerializedName("volume_24h") val volume24h: Double,
    @SerializedName("percent_change_1h") val percentChange1h: Double,
    @SerializedName("percent_change_24h") val percentChange24h: Double,
    @SerializedName("percent_change_7d") val percentChange7d: Double,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("last_updated") val lastUpdated: String
)