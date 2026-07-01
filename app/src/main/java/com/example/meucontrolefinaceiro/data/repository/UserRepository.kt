package com.example.meucontrolefinaceiro.data.repository

import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDAO: SqLiteDAO) {

    suspend fun lerDados(): List<String>{
        return withContext(Dispatchers.IO){
            userDAO.SQLiteLer()
        }
    }

    suspend fun adicionarDados(idUser: String, saldoTotal: String, debitoTotal: String){
        return withContext(Dispatchers.IO){
            userDAO.SQLiteAdd(idUser, saldoTotal, debitoTotal)
        }
    }

    suspend fun atualizarDados(idUser: String, saldoTotalNovo: String, debitoTotalNovo: String): Int{
        return withContext(Dispatchers.IO) {
            userDAO.atualizarDados(idUser, saldoTotalNovo, debitoTotalNovo)
        }
    }

}