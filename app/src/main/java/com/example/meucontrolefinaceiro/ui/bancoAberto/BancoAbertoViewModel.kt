package com.example.meucontrolefinaceiro.ui.bancoAberto

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.data.repository.HomeRepository
import com.example.meucontrolefinaceiro.data.sqlite.BancoDeDados
import com.example.meucontrolefinaceiro.data.sqlite.SqLiteDAO
import com.example.meucontrolefinaceiro.utils.dobleToReal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class BancoAbertoViewModel(application: Application) : AndroidViewModel(application) {

    var nomeConta: String = ""
    var tipoConta: String = ""
    var saldo: Double = 0.0
    var saldoLiq: Double = 0.0
    var debito: Double = 0.0


    data class DadosDaConta(
        val nomeConta: String,
        val tipoConta: String,
        val saldo: String,
        val saldoLiq: String,
        val debito: String
    )

    private val _addTrazacaoStatus = MutableLiveData<Int?>()
    val addTrazacaoStatus = _addTrazacaoStatus

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorValorAddTrz = MutableStateFlow<Int?>(null)
    val erroValorAddTrz: StateFlow<Int?> = _errorValorAddTrz

    private val _errorValorEdit = MutableLiveData<Int?>(null)
    val erroValorEdit: LiveData<Int?> = _errorValorEdit


    private val _dadosBanco = MutableLiveData<DadosDaConta>()
    val dadosBanco: LiveData<DadosDaConta> = _dadosBanco

    private val bancoDeDados = BancoDeDados(application)
    private val dao = SqLiteDAO(bancoDeDados)
    private val repositori = HomeRepository(dao)


    fun buscarDados(idUser: String, idConta: String?, tipo: String, valor: String) {
        _errorValorAddTrz.value = null

        if (valor.isBlank()) {
            _errorValorAddTrz.value = R.string.addTraz_failed
            return
        }

        if (idConta == null) {
            _errorValorAddTrz.value = R.string.addTraz_failed
            return
        }


        val valorEmNum = valor.toDoubleOrNull()!!
        viewModelScope.launch {
            carregarDados(idUser, idConta, tipo, valorEmNum)
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
                    atualizarDados(idUser, idConta)

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
                    atualizarDados(idUser, idConta)

                }
                .addOnFailureListener { error ->
                    _loading.value = false
                    _addTrazacaoStatus.value = R.string.addTraz_failed2
                }

        }
    }

    fun editarSaldo(idUser: String, idConta: String?, valor: String) {
        _errorValorEdit.value = null
        _loading.value = true

        if (valor.isBlank()) {
            _errorValorEdit.value = R.string.addTraz_failed
            _loading.value = false
            return
        }

        val valorEmDouble = valor.toDoubleOrNull()

        viewModelScope.launch {
            val ref = FirebaseFirestore.getInstance()
                .collection("usuario")
                .document(idUser)
                .collection("Contas")
                .document(idConta!!)
            ref.update("saldo", valorEmDouble)
                .addOnSuccessListener {
                    _loading.value = false
                    atualizarDados(idUser, idConta)

                }
                .addOnFailureListener {
                    _loading.value = false
                }
        }
    }

    fun atualizarDados(idUser: String, idConta: String) {
        val ref = FirebaseFirestore.getInstance().collection("usuario")
            .document(idUser).collection("Contas").document(idConta)

        ref.get()
            .addOnSuccessListener { snapshot ->
                val saldoAtual = snapshot.getDouble("saldo")!!
                val debitoAtual = snapshot.getDouble("debito")!!
                val newLiquido = saldoAtual - debitoAtual
               val nomeConta = snapshot.getString("nomeConta").toString()
                val tipoConta = snapshot.getString("tipoConta").toString()
                val saldo = snapshot.getDouble("saldo")!!
                val saldoLiq = newLiquido
                val debito = snapshot.getDouble("debito")!!


                val formatSaldo = dobleToReal(saldo)
                val formatsaldoLiq = dobleToReal(saldoLiq)
                val formatdebito = dobleToReal(debito)

                viewModelScope.launch {
                    repositori.adicionarDados(idConta, "contaCorrente",saldo.toString(), debito.toString())
                }

                val dados = DadosDaConta(
                    nomeConta = nomeConta,
                    tipoConta = tipoConta,
                    saldo = formatSaldo,
                    saldoLiq = formatsaldoLiq,
                    debito = formatdebito
                )


                _dadosBanco.postValue(dados)
            }
    }

}


