package com.example.meucontrolefinaceiro.data.sqlite

import android.content.ContentValues
import com.example.meucontrolefinaceiro.utils.Constants

class SqLiteDAO(private val dbHeloer: BancoDeDados ) {

    fun SQLiteLer(idUser: String): List<String>{

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

    fun  SQLiteRemover(idUser: String){
        val db = dbHeloer.writableDatabase
        db.delete(Constants.SQLite, null, null)
    }

}