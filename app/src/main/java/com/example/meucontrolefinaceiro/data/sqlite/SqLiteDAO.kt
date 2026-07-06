package com.example.meucontrolefinaceiro.data.sqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.meucontrolefinaceiro.utils.Constants
import com.example.meucontrolefinaceiro.utils.dobleToReal

class SqLiteDAO(private val dbHelper: BancoDeDados ) {
    data class TotalFinanceiro(
        val totalSaldo: String,
        val totalDebito: String
    )

    fun obterTotaisPorTipo(tipoBuscado: String): TotalFinanceiro {
        val db = dbHelper.readableDatabase
        var saldoSomado = "0.0"
        var debitoSomado = "0.0"

        val sql = "SELECT SUM(saldoTotal), SUM(debitoTotal) FROM ${Constants.SQLite} WHERE tipoConta = ?"

        val cursor = db.rawQuery(sql, arrayOf(tipoBuscado))

        if (cursor.moveToFirst()) {
            saldoSomado = dobleToReal(cursor.getDouble(0))
            debitoSomado = dobleToReal(cursor.getDouble(1))
        }

        cursor.close()
        return TotalFinanceiro(saldoSomado, debitoSomado)
    }
    fun SQLiteAdd(idConta: String, tipoConta: String, saldo: String, debito: String){
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("idConta", idConta)
            put("tipoConta", tipoConta)
            put("saldoTotal", saldo)
            put("debitoTotal", debito)
        }

        db.insertWithOnConflict(Constants.SQLite, null, values, SQLiteDatabase.CONFLICT_REPLACE )
    }

    fun  SQLiteRemover(item: String){
        //Não util no momento
    }

}