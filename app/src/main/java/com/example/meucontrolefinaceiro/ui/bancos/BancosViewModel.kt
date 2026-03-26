package com.example.meucontrolefinaceiro.ui.bancos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.utils.constantes
import com.google.firebase.firestore.FirebaseFirestore

class BancosViewModel : ViewModel() {

    private val _erroNome = MutableLiveData<Int?>()
    val erroNome: LiveData<Int?> = _erroNome

    private val _erroRadio = MutableLiveData<Int?>()
    val erroRadio: LiveData<Int?> = _erroRadio

    private val _salvarStatus = MutableLiveData<String?>()
    val salvarStatus: LiveData<String?> = _salvarStatus

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading




    fun adicionarNovaConta(userId: String, nome : String, isCorrente: Boolean){
        _loading.value = true

            if (nome.isEmpty()){
                _erroNome.value = R.string.add_error_name
                _loading.value = false
                return
            }

            _erroNome.value = null

            //tipo
            val tipoDeConta = if (isCorrente) "contaCorrente" else "contaInvestimentos"

            val dadosDaConta = mapOf(
                "nomeConta" to nome,
                "tipoConta" to tipoDeConta
            )

            salvarFirebase(userId, nome, dadosDaConta)

    }

    fun salvarFirebase(idUser: String, nomeConta: String, contasInfo: Map<String, String>){
        FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUser)
            .collection("Contas")
            .document()
            .set(contasInfo)
            .addOnSuccessListener {
                _salvarStatus.value = "salvo"
                _loading.value = false
            }
            .addOnFailureListener { error->
                _salvarStatus.value = "erro"
                _loading.value = false

            }

    }
}