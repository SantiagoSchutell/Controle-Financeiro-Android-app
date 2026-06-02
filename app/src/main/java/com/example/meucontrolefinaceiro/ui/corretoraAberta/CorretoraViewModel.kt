package com.example.meucontrolefinaceiro.ui.corretoraAberta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.Data.model.Corretora
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.utils.constantes
import com.example.meucontrolefinaceiro.utils.dobleToReal
import com.google.firebase.firestore.FirebaseFirestore

class CorretoraViewModel: ViewModel() {
    private val _erroSalvar = MutableLiveData<Int?>()
    val erroSalvar: LiveData<Int?> = _erroSalvar

    private val _errocarregar = MutableLiveData<Int?>()
    val erroBuscar: LiveData<Int?> = _errocarregar

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _listaAtivos = MutableLiveData<List<Corretora>>()
    val listaAtivos: LiveData<List<Corretora>> = _listaAtivos


    ///Implementar a Função de APAGAR e Abrir pra editar o valor

    fun salvarNovoAtivo(idUsuario: String, idConta: String, nomeAtivo: String){
        _loading.value = true

        if (nomeAtivo.isNullOrEmpty()){
            _loading.value = false
            _erroSalvar.value = R.string.valorNull
            return
        }
        if (idUsuario.isNullOrEmpty()){
            _loading.value = false
            _erroSalvar.value = R.string.valorIdUser
            return
        }
        if (idConta.isNullOrEmpty()){
            _loading.value = false
            _erroSalvar.value = R.string.valorIdConta
            return
        }

        val ref = FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUsuario)
            .collection(constantes.CONTAS)
            .document(idConta)
            .collection(idConta)


        val novoDocRef = ref.document()
        val idGerado = novoDocRef.id

        val data = mapOf(
            "idOperacao" to idConta,
            "idAtivo" to  idGerado,
            "AtivoNome" to nomeAtivo,
            "saldo" to 0)


        novoDocRef.set(data)
            .addOnSuccessListener {doc->
                _loading.value = false
            }
            .addOnFailureListener {
                _loading.value = false
                _erroSalvar.value = R.string.valorErroA
            }
    }
    fun carregarAtivo(idUsuario: String, idConta: String){
        if (idUsuario.isNullOrEmpty()){
            _errocarregar.value = R.string.valorIdUser
            _loading.value = false
            return
        }
        if (idConta.isNullOrEmpty()){
            _errocarregar.value = R.string.valorIdConta
            _loading.value = false
            return
        }

        val ref = FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUsuario)
            .collection(constantes.CONTAS)
            .document(idConta)
            .collection(idConta)

            ref.addSnapshotListener(){value, error ->
                if (error!=null){
                   _errocarregar.value = R.string.BuscarErro
                    _loading.value = false
                }

                val lista = value?.mapNotNull { doc->
                    Corretora(doc.getString("idAtivo")?:"null", dobleToReal(doc.getDouble("saldo")!!), doc.getString("AtivoNome")?:"null")
                }?: emptyList()

                _listaAtivos.value = lista

            }

    }
}