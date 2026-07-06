package com.example.meucontrolefinaceiro.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.meucontrolefinaceiro.utils.Constants

class  BancoDeDados(context: Context) : SQLiteOpenHelper(context, Constants.SQLite, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${Constants.SQLite} (idConta TEXT PRIMARY KEY, tipoConta TEXT, saldoTotal TEXT, debitoTotal TEXT);")
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("ALTER TABLE ${Constants.SQLite} ADD COLUMN nome TEXT;")
        onCreate(db)
    }


}