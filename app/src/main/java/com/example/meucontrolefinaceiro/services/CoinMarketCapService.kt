package com.example.meucontrolefinaceiro.services

import com.example.meucontrolefinaceiro.Data.CoinMarketCapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketCapService {

    companion object {

        const val API_KEY = "6a3954f4-f344-42ed-87f1-79c2ed86be6d"
        const val BASE_URL = "https://pro-api.coinmarketcap.com/"
    }

    @Headers("X-CMC_PRO_API_KEY: $API_KEY") //
    @GET("v1/cryptocurrency/quotes/latest")
    suspend fun getCryptoQuotes(
        @Query("symbol") symbol: String = "BTC",
        @Query("convert") convertCurrency: String = "BRL"
    ): Response<CoinMarketCapResponse>
}