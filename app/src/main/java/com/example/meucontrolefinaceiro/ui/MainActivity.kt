package com.example.meucontrolefinaceiro.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.ActivityMainBinding
import com.example.meucontrolefinaceiro.services.CoinMarketCapService
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchBitcoinPriceCMC() {
        val retrofit = Retrofit.Builder()
            .baseUrl(CoinMarketCapService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CoinMarketCapService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.getCryptoQuotes(symbol = "BTC", convertCurrency = "BRL")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val cryptoData = response.body()?.data?.get("BTC")
                        val brlQuote = cryptoData?.quote?.get("BRL")
                        if (brlQuote != null) {
                            var bitcoinAtualPrice = brlQuote.price
                            Log.d("CoinMarketCap", "Valor salvo na variável: $bitcoinAtualPrice")
                            /*    cryptoBRL = bitcoinAtualPrice * crypto
                                binding.textSaldoInvestido.text = formatarParaDinheiro(xpInvestimentos + cryptoBRL+ previdencia)
                                investimentos = xpInvestimentos + cryptoBRL + previdencia
                                valorTotal = saldosEmContaLiquido + investimentos
                                binding.textSaldoTotal.text = formatarParaDinheiro(valorTotal)
                                Log.d("CoinMarketCap", "$: $cryptoBRL")*/
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("CoinMarketCap", "Erro de rede ao buscar preço BTC: ${e.message}", e)
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()

    }
}