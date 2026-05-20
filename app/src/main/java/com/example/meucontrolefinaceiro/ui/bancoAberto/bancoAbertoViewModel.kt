package com.example.meucontrolefinaceiro.ui.bancoAberto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meucontrolefinaceiro.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class bancoAbertoViewModel : ViewModel() {

    var nomeConta: String = ""
    var tipoConta: String = ""
    var saldo: Double = 0.0
    var saldoLiq: Double = 0.0
    var debito: Double = 0.0

    private val _addTrazacaoStatus = MutableLiveData<Int?>()
    val addTrazacaoStatus = _addTrazacaoStatus

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading

    fun buscarDados(idUser: String, idConta: String, tipo: String, valor: Double) {
        viewModelScope.launch {
            carregarDados(idUser, idConta, tipo, valor)
        }
    }

    suspend fun carregarDados(idUser: String, idConta: String, tipo: String, valor: Double) {
        val ref = FirebaseFirestore.getInstance().collection("usuario")
            .document(idUser).collection("Contas").document(idConta)
        try {
            val snapshot = ref.get().await()
            if (snapshot.exists()) {
                nomeConta = snapshot.getString("nomeConta").toString()
                tipoConta = snapshot.getString("tipoConta").toString()
                saldo = snapshot.getDouble("saldo")!!
                saldoLiq = snapshot.getDouble("saldoLiq")!!
                debito = snapshot.getDouble("debito")!!

                adicionarTrazaçoes(idUser, idConta, tipo, valor)

            } else {
                Log.i("ErroCarregarDados", "Documentos não encontrado")
            }
        } catch (E: Exception) {
            Log.i("ErroCarregarDados", "Erro: ${E.message}")
        }
    }

    fun adicionarTrazaçoes(idUser: String, idConta: String, tipo: String, valor: Double) {
        _loading.value = true
        if (valor == null) {
            _addTrazacaoStatus.value = R.string.addTraz_failed
            _loading.value = false
            return
        }

        val newSaldoDeb = debito + valor
        val newSaldo = saldo + valor
        val newSaldoLiq = saldo + valor - debito
        val newSaldoLiqDeb = saldo - valor - debito

        val ref = FirebaseFirestore.getInstance()
            .collection("usuario")
            .document(idUser)
            .collection("Contas")
            .document(idConta)


        if (tipo == "credito") {
            val valores = mapOf(
                "saldo" to newSaldo,
                "saldoLiq" to newSaldoLiq
            )
            ref.update(valores)
                .addOnSuccessListener {
                    _loading.value = false
                    _addTrazacaoStatus.value = R.string.addTraz_Sucess
                }
                .addOnFailureListener { error ->
                    _loading.value = false
                    _addTrazacaoStatus.value = R.string.addTraz_failed2
                }


        } else {
            val valores = mapOf(
                "debito" to newSaldoDeb,
                "saldoLiq" to newSaldoLiqDeb

            )
            ref.update(valores)
                .addOnSuccessListener {
                    _loading.value = false
                    _addTrazacaoStatus.value = R.string.addTraz_Sucess
                }
                .addOnFailureListener { error ->
                    _loading.value = false
                    _addTrazacaoStatus.value = R.string.addTraz_failed2
                }

        }
    }
}
