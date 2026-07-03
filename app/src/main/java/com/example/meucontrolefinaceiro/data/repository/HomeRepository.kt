package com.example.meucontrolefinaceiro.data.repository

import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val userDAO: SqLiteDAO) {

    suspend fun lerDados(): List<String>{
        return withContext(Dispatchers.IO){
            userDAO.SQLiteLer()
        }
    }

    suspend fun adicionarDados(contaNome: String, saldoTotal: String, debitoTotal: String){
        return withContext(Dispatchers.IO){
            userDAO.SQLiteAdd(contaNome, saldoTotal, debitoTotal)
        }
    }

    suspend fun atualizarDados(contaNome: String, saldoTotalNovo: String, debitoTotalNovo: String): Int{
        return withContext(Dispatchers.IO) {
            userDAO.atualizarDados(contaNome, saldoTotalNovo, debitoTotalNovo)
        }
    }

}