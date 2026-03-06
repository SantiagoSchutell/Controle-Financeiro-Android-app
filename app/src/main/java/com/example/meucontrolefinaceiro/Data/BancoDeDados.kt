package com.example.meucontrolefinaceiro.Data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception
import java.math.BigInteger
import java.sql.Date
import java.text.SimpleDateFormat
import kotlin.text.toDouble

class  BancoDeDados(context: Context) : SQLiteOpenHelper(context, "AppData", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val SQl =
            "CREATE TABLE AppData (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " CREDITOS DECIMAL, " +
                    "DEBITOS DECIMAL, " +
                    "CREDITOALIOS DECIMAL," +
                    " DEBITOALIOS DECIMAL," +
                    "CREDITOSMP DECIMAL," +
                    "DEBITOSMP DECIMAL," +
                    "CREDITOSNU DECIMAL," +
                    "DEBITOSNU DECIMAL," +
                    "CREDITOXPD DECIMAL," +
                    "DEBITOXPD DECIMAL," +
                    "SALDOPREV1 DECIMAL," +
                    "SALDOPREV2 DECIMAL," +
                    "CRYPTOSALDO REAL," +
                    "ACOESXP DECIMAL," +
                    "FIISXP DECIMAL," +
                    "RENDFXP DECIMAL," +
                    "PROVENTOSXP DECIMAL," +
                    "INVCONTXP DECIMAL," +
                    "DATAATT DATA);"

        try {
            db?.execSQL(SQl)
            Log.i("appTestes", "Sucesso ao criar o banco de Dados")

            val addInicialColum =
                "INSERT INTO AppData (id, CREDITOS, DEBITOS, CREDITOALIOS, DEBITOALIOS, CREDITOSMP, DEBITOSMP) VALUES (1, 0, 0, 0, 0, 0, 0);"
            db?.execSQL(addInicialColum)
            Log.i("appTestes", "Registro inicial (id=1) inserido com sucesso!")


        } catch (e: Exception) {
            Log.i("appTestes", "Erro ao criar o banco de Dados")

        }


    }

    fun atualizarAppData(coluna: String, valorAtt: Double, id: Int) {

        val setValor = "UPDATE AppData SET $coluna = $valorAtt WHERE id = $id;"

        try {

            val escrita = writableDatabase.execSQL(setValor)
            Log.i("AppTeste", "Sucesso ao Atualizar dados")

        } catch (e: Exception) {
            Log.i("AppTeste", "Erro ao Atualizar")
        }
    }

    fun atualizarData(data: String) {

        val setValor = "UPDATE AppData SET DATAATT = $data WHERE id = 1;"

        try {

            val escrita = writableDatabase.execSQL(setValor)
            Log.i("AppTeste", "Sucesso ao Atualizar dados")

        } catch (e: Exception) {
            Log.i("AppTeste", "Erro ao Atualizar ${e.message}")
        }
    }

    fun adicionarData( valorAtt: String){
        val escrever = this.readableDatabase
        try {
            escrever.execSQL("INSERT INTO AppData (DATAATT) VALUES ($valorAtt )WHERE id = 1;")
            Log.i("sqlite", "Sucesso ao inserir DATA")
        } catch (e: Exception) {
            Log.i("sqlite", "Erro ao inserir DATA")

        }
    }

    fun atualizarAppDataCrypto(coluna: String, valorAtt: Double, id: Int) {

        val setValor = "UPDATE AppData SET $coluna = $valorAtt WHERE id = $id;"

        try {

            val escrita = writableDatabase.execSQL(setValor)
            Log.i("AppTeste", "Sucesso ao Atualizar dados")

        } catch (e: Exception) {
            Log.i("AppTeste", "Erro ao Atualizar")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //Ailos
    fun recuperarSaldosAlios(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("CREDITOALIOS", "DEBITOALIOS")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOALIOS")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOALIOS")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'CREDITOALIOS' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'DEBITOALIOS' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Alios: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null // Retorna null se não encontrar os dados ou houver erro
    }

    //Mercado Pago
    fun recuperarSaldosMp(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("CREDITOSMP", "DEBITOSMP")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOSMP")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOSMP")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'CREDITOSMP' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'DEBITOSMP' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Mercado Pago: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    //Nubank
    fun recuperarSaldosNu(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("CREDITOSNU", "DEBITOSNU")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOSNU")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOSNU")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'CREDITOSNU' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'DEBITOSNU' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Mercado Pago: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    //Xp conta Digital
    fun recuperarSaldosXPD(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("CREDITOXPD", "DEBITOXPD")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOXPD")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOXPD")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'CREDITOXPD' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'DEBITOXPD' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Mercado Pago: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    //Previdencias Privada
    fun recuperarSaldosPREV(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("SALDOPREV1", "SALDOPREV2")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("SALDOPREV1")
                val debitoAliosIndex = cursor.getColumnIndex("SALDOPREV2")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'SALDOPREV1' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'SALDOPREV2' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Mercado Pago: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    //Cryptomoedas
    fun recuperarSaldosCRYPTO(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = this.readableDatabase // Abre o banco de dados no modo leitura

        val columns = arrayOf("CRYPTOSALDO", "SALDOPREV2")

        var cursor: Cursor? = null // Usando android.database.Cursor diretamente

        try {
            cursor = db.query(
                "AppData",      // Nome da tabela
                columns,         // Colunas que você quer retornar
                "id = ?",        // Cláusula WHERE (seleciona o registro com id = 1)
                arrayOf("1"),    // Argumentos para a cláusula WHERE
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CRYPTOSALDO")
                val debitoAliosIndex = cursor.getColumnIndex("SALDOPREV2")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'CRYPTOSALDO' não encontrada.")
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'SALDOPREV2' não encontrada.")
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao buscar dados Mercado Pago: ${e.message}", e) // Log mais detalhado
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    //Xp Investimentos
    // Dentro de BancoDeDados.kt ou em um arquivo Models.kt, por exemplo
    data class SaldosXPI(
        val acoesXP: Double,
        val fiisXP: Double,
        val rendfXP: Double,
        val proventosXP: Double,
        val invcontXP: Double
    )

    fun recuperarSaldosXPI(): SaldosXPI? {
        var ACOESXP: Double = 0.0
        var FIISXP: Double = 0.0
        var RENDFXP: Double = 0.0
        var PROVENTOSXP: Double = 0.0
        var INVCONTXP: Double = 0.0

        val db = this.readableDatabase

        val columns = arrayOf("ACOESXP", "FIISXP", "RENDFXP", "PROVENTOSXP", "INVCONTXP" )

        var cursor: Cursor? = null

        try {
            cursor = db.query(
                "AppData",
                columns,
                "id = ?",
                arrayOf("1"),
                null,            // groupBy
                null,            // having
                null             // orderBy
            )

            if (cursor.moveToFirst()) {
                val ACOESXPIndex = cursor.getColumnIndex("ACOESXP")
                val FIISXPIndex = cursor.getColumnIndex("FIISXP")
                val RENDFXPIndex = cursor.getColumnIndex("RENDFXP")
                val PROVENTOSXPIndex = cursor.getColumnIndex("PROVENTOSXP")
                val INVCONTXPIndex = cursor.getColumnIndex("INVCONTXP")

                if (ACOESXPIndex != -1) {
                    ACOESXP = cursor.getDouble(ACOESXPIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'ACOESXP' não encontrada.")
                }
                if (FIISXPIndex != -1) {
                    FIISXP = cursor.getDouble(FIISXPIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'FIISXP' não encontrada.")
                }
                if (RENDFXPIndex != -1) {
                    RENDFXP = cursor.getDouble(RENDFXPIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'RENDFXP' não encontrada.")
                }
                if (PROVENTOSXPIndex != -1) {
                    PROVENTOSXP = cursor.getDouble(PROVENTOSXPIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'PROVENTOSXP' não encontrada.")
                }
                if (INVCONTXPIndex != -1) {
                    INVCONTXP = cursor.getDouble(INVCONTXPIndex)
                } else {
                    Log.w("BancoDeDados", "Coluna 'INVCONTXP' não encontrada.")
                }


                return SaldosXPI(ACOESXP, FIISXP, RENDFXP, PROVENTOSXP, INVCONTXP)


            }
        } catch (e: Exception) {
            Log.e("BancoDeDados", "Erro ao recuperar saldos XPI: ${e.message}", e)
        } finally {
            cursor?.close()
        }
        return null
    }

    fun obterData(): String? {
        var dataAtt = "sem dados"
        val db = this.readableDatabase
        val query = "SELECT DATAATT FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val dataAttIndex = cursor.getColumnIndex("DATAATT")

                if (dataAttIndex != -1) {
                    dataAtt = cursor.getString(dataAttIndex)
                }
                return dataAtt
            }
        } catch (e: kotlin.Exception) {
            Log.i("AppData", "Erro ao buscar Data de atualização", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

}