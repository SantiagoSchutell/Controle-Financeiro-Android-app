package com.example.meucontrolefinaceiro.ui.corretoraAberta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.data.model.Corretora
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.data.repository.AuthRepositoryImp
import com.example.meucontrolefinaceiro.utils.Constants
import com.example.meucontrolefinaceiro.utils.dobleToReal
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class CorretoraViewModel @Inject constructor(private val authRepository: AuthRepositoryImp) :
    ViewModel() {
    private val idUsuario: String? = authRepository.getUserId()
    private val _erroSalvar = MutableLiveData<Int?>()
    val erroSalvar: LiveData<Int?> = _erroSalvar

    private val _errocarregar = MutableLiveData<Int?>()
    val erroBuscar: LiveData<Int?> = _errocarregar

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _listaAtivos = MutableLiveData<List<Corretora>>()
    val listaAtivos: LiveData<List<Corretora>> = _listaAtivos

    private val _erroApagar = MutableLiveData<Int?>()
    val erroApagar: LiveData<Int?> = _erroApagar

    private val _erroAbrirAtivo = MutableLiveData<Int>()
    val erroAbrirAtivo: LiveData<Int> = _erroAbrirAtivo

    private val _editarStatus = MutableLiveData<Int>()
    val editarStatus: LiveData<Int> = _editarStatus
    private val _saldoTotal = MutableLiveData<String?>()
    val saldoTotal: LiveData<String?> = _saldoTotal


    fun editarAtivo(idAtivo: String?, idConta: String?, newValor: Double) {
        _loading.value = true

        if (idAtivo != null && idConta != null) {
            val ref = FirebaseFirestore.getInstance()
                .collection(Constants.USER)
                .document(idUsuario!!)
                .collection(Constants.CONTAS)
                .document(idConta)
                .collection(idConta)
                .document(idAtivo)

            ref.get().addOnSuccessListener { snapshots ->
                val valores = mapOf(
                    "saldo" to newValor
                )

                ref.update(valores).addOnSuccessListener {
                    _editarStatus.value = R.string.editarSucess
                    _loading.value = false

                }.addOnFailureListener { error->
                    _erroAbrirAtivo.value = R.string.editarErro2
                    _loading.value = false

                }
            }
        } else {
            _erroAbrirAtivo.value = R.string.editarErro
            _loading.value = false
        }
    }

    fun apagarAtivo(idConta: String?, idAtivo: String?) {
        if (idUsuario == null) {
            _loading.value = false
            return
        }
        if (idConta == null) {
            _loading.value = false
            return
        }
        if (idAtivo == null) {
            _loading.value = false
            return
        }
        _loading.value = true
        val ref = FirebaseFirestore.getInstance()
            .collection(Constants.USER)
            .document(idUsuario)
            .collection(Constants.CONTAS)
            .document(idConta)
            .collection(idConta)
            .document(idAtivo)

        ref.delete()
            .addOnSuccessListener {
                _loading.value = false
            }
            .addOnFailureListener {
                _loading.value = false
                _erroApagar.value = R.string.ApagarErro
            }

    }

    fun salvarNovoAtivo(idConta: String?, nomeAtivo: String?) {
        _loading.value = true

        if (nomeAtivo.isNullOrEmpty()) {
            _loading.value = false
            _erroSalvar.value = R.string.valorNull
            return
        }
        if (idUsuario.isNullOrEmpty()) {
            _loading.value = false
            _erroSalvar.value = R.string.valorIdUser
            return
        }
        if (idConta.isNullOrEmpty()) {
            _loading.value = false
            _erroSalvar.value = R.string.valorIdConta
            return
        }

        val ref = FirebaseFirestore.getInstance()
            .collection(Constants.USER)
            .document(idUsuario)
            .collection(Constants.CONTAS)
            .document(idConta)
            .collection(idConta)


        val novoDocRef = ref.document()
        val idGerado = novoDocRef.id

        val data = mapOf(
            "idOperacao" to idConta,
            "idAtivo" to idGerado,
            "AtivoNome" to nomeAtivo,
            "saldo" to 0
        )


        novoDocRef.set(data)
            .addOnSuccessListener { doc ->
                _loading.value = false
            }
            .addOnFailureListener {
                _loading.value = false
                _erroSalvar.value = R.string.valorErroA
            }
    }

    fun carregarAtivo(idConta: String?) {
        if (idUsuario.isNullOrEmpty()) {
            _errocarregar.value = R.string.valorIdUser
            _loading.value = false
            return
        }
        if (idConta.isNullOrEmpty()) {
            _errocarregar.value = R.string.valorIdConta
            _loading.value = false
            return
        }

        val ref = FirebaseFirestore.getInstance()
            .collection(Constants.USER)
            .document(idUsuario)
            .collection(Constants.CONTAS)
            .document(idConta)
            .collection(idConta)

        ref.addSnapshotListener() { value, error ->
            if (error != null) {
                _errocarregar.value = R.string.BuscarErro
                _loading.value = false
            }

            val lista = value?.mapNotNull { doc ->
                Corretora(
                    doc.getString("idAtivo") ?: "null",
                    dobleToReal(doc.getDouble("saldo")!!),
                    doc.getString("AtivoNome") ?: "null"

                )
            } ?: emptyList()

            val somaTotal = value?.documents?.sumOf { doc ->
                doc.getDouble("saldo") ?: 0.0
            } ?: 0.0

            _saldoTotal.value = dobleToReal(somaTotal)
            _listaAtivos.value = lista

        }

    }
}