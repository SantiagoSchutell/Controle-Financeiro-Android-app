package com.example.meucontrolefinaceiro.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.meucontrolefinaceiro.data.repository.HomeRepository
import com.example.meucontrolefinaceiro.data.sqlite.BancoDeDados
import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import com.example.meucontrolefinaceiro.utils.dobleToReal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    data class ResumoFinanceiroUiState(
        val contaSaldoString: String = "0.0",
        val contaDebitoString: String = "0.0",
        val corretoraSaldoString: String = "0.0",
        val totalSaldos: String = "0.0"
    )


    private val _resumoFinanceiro = MutableStateFlow<Pair<SqLiteDAO.TotalFinanceiro, SqLiteDAO.TotalFinanceiro>?>(null)
    val resumoFinanceiro: StateFlow<Pair<SqLiteDAO.TotalFinanceiro, SqLiteDAO.TotalFinanceiro>?> = _resumoFinanceiro

    private val bancoDeDados = BancoDeDados(application)
    private  val dao = SqLiteDAO(bancoDeDados)
    private val repository = HomeRepository(dao)

    val resumoParaTela: StateFlow<ResumoFinanceiroUiState> = _resumoFinanceiro
        .filterNotNull()
        .map { parDeResultados ->

            val contaCorrente = parDeResultados.second
            val corretora = parDeResultados.first
            val totalSaldos = corretora.totalSaldo + contaCorrente.totalSaldo

            val correnteSaldo = dobleToReal(contaCorrente.totalSaldo)
            val correnteDebito = dobleToReal(contaCorrente.totalDebito)
            val total = dobleToReal(totalSaldos)
            val corretoraSaldos = dobleToReal(corretora.totalSaldo)

            ResumoFinanceiroUiState(
                contaSaldoString = correnteSaldo,
                contaDebitoString = correnteDebito,
                corretoraSaldoString = corretoraSaldos,
                totalSaldos = total
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ResumoFinanceiroUiState()
        )
     fun buscarSaldos(){
        viewModelScope.launch {
            val resultadoQueVeioDoBanco = repository.buscarResumoHome()
            _resumoFinanceiro.value = resultadoQueVeioDoBanco
        }
    }


}