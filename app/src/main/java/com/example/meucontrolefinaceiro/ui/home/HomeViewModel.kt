package com.example.meucontrolefinaceiro.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meucontrolefinaceiro.data.model.Dadosbanco
import com.example.meucontrolefinaceiro.data.repository.HomeRepository
import com.example.meucontrolefinaceiro.data.sqlite.BancoDeDados
import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _totalSaldo = MutableLiveData<String?>()
    val totalSaldo: LiveData<String?> = _totalSaldo

    private val _totalDebitos = MutableLiveData<String?>()
    val totalDebitos: LiveData<String?> = _totalDebitos

    private val _totalSaldoConta = MutableLiveData<String?>()
    val totalSaldoConta: LiveData<String?> = _totalSaldoConta

    private val _totalInvestido = MutableLiveData<String?>()
    val totalInvestido: LiveData<String?> = _totalInvestido

    private val _erroBuscar = MutableLiveData<Int?>()
    val erroBuscar: LiveData<Int?> = _erroBuscar

    private val _erroLogin = MutableLiveData<Int?>()
    val erroLogin: LiveData<Int?> = _erroLogin

    private val _dadosConta = MutableLiveData<Dadosbanco>()
    val dadosConta: MutableLiveData<Dadosbanco> = _dadosConta

    private val _resumoFinanceiro = MutableStateFlow<Pair<SqLiteDAO.TotalFinanceiro, SqLiteDAO.TotalFinanceiro>?>(null)
    val resumoFinanceiro: StateFlow<Pair<SqLiteDAO.TotalFinanceiro, SqLiteDAO.TotalFinanceiro>?> = _resumoFinanceiro

    private val bancoDeDados = BancoDeDados(application)
    private  val dao = SqLiteDAO(bancoDeDados)
    private val repository = HomeRepository(dao)

     fun buscarSaldos(){
        viewModelScope.launch {
            val resultadoQueVeioDoBanco = repository.buscarResumoHome()
            _resumoFinanceiro.value = resultadoQueVeioDoBanco
        }
    }


}