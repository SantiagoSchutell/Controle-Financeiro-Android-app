package com.example.meucontrolefinaceiro.data.sqlite

import android.content.ContentValues
import com.example.meucontrolefinaceiro.utils.Constants

class SqLiteDAO(private val dbHeloer: BancoDeDados ) {

    fun SQLiteLer(): List<String>{

        val db = dbHeloer.readableDatabase
        val dadosSQLite = mutableListOf<String>()
        val resultado = db.query(Constants.SQLite, null, null, null, null, null, null)

        if (resultado.moveToFirst()){
            do {
                val saldoTotal = resultado.getString(resultado.getColumnIndexOrThrow("SaldoTotal"))
                dadosSQLite.add(saldoTotal)
                val debitoTotal = resultado.getString(resultado.getColumnIndexOrThrow("debitoTotal"))
                dadosSQLite.add(debitoTotal)

            }while (resultado.moveToNext())
        }

        return dadosSQLite
    }

    fun SQLiteAdd(idUser: String, saldoTotal: String, debitoTotal: String): Boolean{
        val db = dbHeloer.writableDatabase
        val values = ContentValues().apply {
            put("idUser", idUser)
            put("SaldoTotal", saldoTotal)
            put("debitoTotal", debitoTotal)
        }
        val resultado = db.insert(Constants.SQLite, null, values)
        if(resultado == -1L){
            return false
        }

        return true
    }

    fun atualizarDados(idUser: String, saldoTotalNovo: String, debitoTotalNovo: String): Int{
        val valores = ContentValues().apply {
            put("SaldoTotal", saldoTotalNovo)
            put("debitoTotal", debitoTotalNovo)
        }
        val db = dbHeloer.writableDatabase
        return db.update(Constants.SQLite, valores, "idUser = ?", arrayOf(idUser))
    }

    fun  SQLiteRemover(item: String){
        //Não util no momento
    }

}