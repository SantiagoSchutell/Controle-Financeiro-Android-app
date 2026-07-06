package com.example.meucontrolefinaceiro.data.repository

import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val userDAO: SqLiteDAO) {
    suspend fun adicionarDados(idConta: String, tipoConta: String, saldoTotal: String, debitoTotal: String){
         withContext(Dispatchers.IO){
            userDAO.SQLiteAdd(idConta, tipoConta, saldoTotal, debitoTotal)
        }
    }

/*
    suspend fun atualizarDados(operacao: Int, tipoConta: String, saldoTotalNovo: String, debitoTotalNovo: String){
         withContext(Dispatchers.IO) {
            userDAO.atualizarDados(operacao, saldoTotalNovo, debitoTotalNovo,)
        }
    }*/
    suspend fun buscarResumoHome(): Pair<SqLiteDAO.TotalFinanceiro, SqLiteDAO.TotalFinanceiro> {
        return withContext(Dispatchers.IO) {
            val corretoras = userDAO.obterTotaisPorTipo("corretora")
            val contaCorrente = userDAO.obterTotaisPorTipo("contaCorrente")

            Pair(corretoras, contaCorrente)
        }
    }

}