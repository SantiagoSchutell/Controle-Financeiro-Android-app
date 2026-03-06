package com.example.meucontrolefinaceiro.services

import android.content.Context
import android.database.Cursor
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.meucontrolefinaceiro.Data.BancoDeDados
import java.math.BigInteger
import java.util.Date
import kotlin.text.toDouble

class functions (private val context: Context) {

        private val Appdata by lazy {
            BancoDeDados(context)
        }

    data class SaldosXPI(
        val acoesXP: Double,
        val fiisXP: Double,
        val rendfXP: Double,
        val proventosXP: Double,
        val invcontXP: Double
    )
    fun obterDadosAlios(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT CREDITOALIOS, DEBITOALIOS FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOALIOS")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOALIOS")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosMp(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT CREDITOSMP, DEBITOSMP FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOSMP")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOSMP")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosNu(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT CREDITOSNU, DEBITOSNU FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOSNU")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOSNU")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosXPD(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT CREDITOXPD, DEBITOXPD FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CREDITOXPD")
                val debitoAliosIndex = cursor.getColumnIndex("DEBITOXPD")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosPREV(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT SALDOPREV1, SALDOPREV2 FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("SALDOPREV1")
                val debitoAliosIndex = cursor.getColumnIndex("SALDOPREV2")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosCRYPTO(): Pair<Double, Double>? {
        var creditoAlios: Double = 0.0
        var debitoAlios: Double = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT CRYPTOSALDO, SALDOPREV2 FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val creditoAliosIndex = cursor.getColumnIndex("CRYPTOSALDO")
                val debitoAliosIndex = cursor.getColumnIndex("SALDOPREV2")

                if (creditoAliosIndex != -1) {
                    creditoAlios = cursor.getDouble(creditoAliosIndex)
                }
                if (debitoAliosIndex != -1) {
                    debitoAlios = cursor.getDouble(debitoAliosIndex)
                }
                return Pair(creditoAlios, debitoAlios)
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar dados Alios", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }

    fun obterDadosXPI(): SaldosXPI? {
        var acoesXP: Double = 0.0
        var fiisXP: Double = 0.0
        var rendfXP: Double = 0.0
        var proventosXP: Double = 0.0
        var invcontXP: Double = 0.0

        val db = Appdata.readableDatabase // Acessa o banco de dados através de Appdata
        var cursor: Cursor? = null

        try {
            val query = "SELECT ACOESXP, FIISXP, RENDFXP, PROVENTOSXP, INVCONTXP FROM AppData WHERE id = 1"
            cursor = db.rawQuery(query, null)

            if (cursor.moveToFirst()) {
                val acoesXPIndex = cursor.getColumnIndex("ACOESXP")
                val fiisXPIndex = cursor.getColumnIndex("FIISXP")
                val rendfXPIndex = cursor.getColumnIndex("RENDFXP")
                val proventosXPIndex = cursor.getColumnIndex("PROVENTOSXP")
                val invcontXPIndex = cursor.getColumnIndex("INVCONTXP")

                if (acoesXPIndex != -1) {
                    acoesXP = cursor.getDouble(acoesXPIndex)
                } else {
                    Log.w("functions", "Coluna 'ACOESXP' não encontrada.")
                }
                if (fiisXPIndex != -1) {
                    fiisXP = cursor.getDouble(fiisXPIndex)
                } else {
                    Log.w("functions", "Coluna 'FIISXP' não encontrada.")
                }
                if (rendfXPIndex != -1) {
                    rendfXP = cursor.getDouble(rendfXPIndex)
                } else {
                    Log.w("functions", "Coluna 'RENDFXP' não encontrada.")
                }
                if (proventosXPIndex != -1) {
                    proventosXP = cursor.getDouble(proventosXPIndex)
                } else {
                    Log.w("functions", "Coluna 'PROVENTOSXP' não encontrada.")
                }
                if (invcontXPIndex != -1) {
                    invcontXP = cursor.getDouble(invcontXPIndex)
                } else {
                    Log.w("functions", "Coluna 'INVCONTXP' não encontrada.")
                }

                return SaldosXPI(acoesXP, fiisXP, rendfXP, proventosXP, invcontXP)
            }
        } catch (e: Exception) {
            Log.e("functions", "Erro ao buscar dados PREV da XPI: ${e.message}", e)
        } finally {
            cursor?.close()
            // Não é necessário fechar 'db' aqui, pois 'Appdata.readableDatabase' gerencia a conexão.
            // A instância do banco de dados (SQLiteOpenHelper) cuidará da abertura e fechamento.
        }
        return null
    }

    fun obterData(): Double? {
        var dataAtt = 0.0
        val db = Appdata.readableDatabase
        val query = "SELECT DATAATT FROM AppData WHERE id = 1"

        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                val dataAttIndex = cursor.getColumnIndex("DATAATT")

                if (dataAttIndex != -1) {
                    dataAtt = cursor.getDouble(dataAttIndex)
                }
                return dataAtt
            }
        } catch (e: Exception) {
            Log.i("AppData", "Erro ao buscar Data de atualização", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return null
    }


    //Ailos
    fun aliosAddCredito(CreditoValor: Double){
        Appdata.atualizarAppData("CREDITOALIOS", CreditoValor, 1)
    }

    fun aliosAddDebito(CreditoValor: Double){
        Appdata.atualizarAppData("DEBITOALIOS", CreditoValor, 1)
    }

    //Mercado Pago
    fun MercadoPagAddCredito(CreditoValor: Double){
        Appdata.atualizarAppData("CREDITOSMP", CreditoValor, 1)
    }

    fun MercadoPagAddDebito(CreditoValor: Double){
        Appdata.atualizarAppData("DEBITOSMP", CreditoValor, 1)
    }

    //Nubank
    fun NubankPagAddCredito(CreditoValor: Double){
        Appdata.atualizarAppData("CREDITOSNU", CreditoValor, 1)
    }

    fun NuBankPagAddDebito(CreditoValor: Double){
        Appdata.atualizarAppData("DEBITOSNU", CreditoValor, 1)
    }

    //XP conta Digital
    fun XPDAddCredito(CreditoValor: Double){
        Appdata.atualizarAppData("CREDITOXPD", CreditoValor, 1)
    }

    fun XPDAddDebito(CreditoValor: Double){
        Appdata.atualizarAppData("DEBITOXPD", CreditoValor, 1)
    }

    //Previdencia Privada
    fun PREVAddCredito(CreditoValor: Double){
        Appdata.atualizarAppData("SALDOPREV1", CreditoValor, 1)
    }

    fun PREVAddDebito(CreditoValor: Double){
        Appdata.atualizarAppData("SALDOPREV2", CreditoValor, 1)
    }

    //Crypto
    fun CRYPTOAddCredito(CreditoValor: Double){
        Appdata.atualizarAppDataCrypto("CRYPTOSALDO", CreditoValor, 1)
    }

    fun CRYPTOAddDebito(CreditoValor: Double){
        Appdata.atualizarAppDataCrypto("SALDOPREV2", CreditoValor, 1)
    }

    //Xp Investimetos
    fun XPIAddACOES(CreditoValor: Double){
        Appdata.atualizarAppData("ACOESXP", CreditoValor, 1)
    }
    fun XPIAddFIIS(CreditoValor: Double){
        Appdata.atualizarAppData("FIISXP", CreditoValor, 1)
    }
    fun XPIAddRENDF(CreditoValor: Double){
        Appdata.atualizarAppData("RENDFXP", CreditoValor, 1)
    }
    fun XPIAddPROVENTOS(CreditoValor: Double){
        Appdata.atualizarAppData("PROVENTOSXP", CreditoValor, 1)
    }

    fun XPIAddINVCONT(CreditoValor: Double){
        Appdata.atualizarAppData("INVCONTXP", CreditoValor, 1)
    }



}
