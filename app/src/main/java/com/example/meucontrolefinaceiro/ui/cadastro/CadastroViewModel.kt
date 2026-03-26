package com.example.meucontrolefinaceiro.ui.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.utils.constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class CadastroViewModel : ViewModel() {
    private val _nomeErro = MutableLiveData<Int>()
    val nomeErro: LiveData<Int?> = _nomeErro

    private val _emailErro = MutableLiveData<Int>()
    val emailErro: LiveData<Int?> = _emailErro

    private val _userSenha = MutableLiveData<Int>()
    val userSenha: LiveData<Int?> = _userSenha

    private val _dadosSalvo = MutableLiveData<Boolean>()
    val dadosSalvo: LiveData<Boolean> = _dadosSalvo

    private val _firebaseErro = MutableLiveData<String>()
    val firebaseErro: LiveData<String?> = _firebaseErro

    private val _cadastroErro = MutableLiveData<String>()
    val cadastroErro: LiveData<String?> = _cadastroErro

    private val _cadastroSucesso = MutableLiveData<Boolean>()
    val cadastroSucesso: LiveData<Boolean> = _cadastroSucesso


    fun verificarCampos(userNome: String, userEmail: String, userSenha: String): Boolean {
        var camposValidos = true

        if (userNome.isNullOrEmpty()) {
            _nomeErro.value = R.string.login_failed_nome
            camposValidos = false
        } else {
            _nomeErro.value = null
        }

        if (userEmail.isNullOrEmpty()) {
            _emailErro.value = R.string.login_failed_email
            camposValidos = false
        } else {
            _emailErro.value = null
        }

        if (userSenha.isNullOrEmpty()) {
            _userSenha.value = R.string.login_failed_password
            camposValidos = false
        } else {
            _userSenha.value = null
        }

        return camposValidos
    }


    fun cadastrarUsuario(nome: String, email: String, senha: String){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener { user ->
                val uid  = user.user?.uid
                if (uid!=null){
                    salvarFirebase(uid, nome)
                }
            }
            .addOnFailureListener { error ->
                _cadastroErro.value = error.message.toString()
            }


    }

    private fun salvarFirebase(idUser: String, userName: String) {
        val data = mapOf(
            "UserName" to userName
        )

        FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUser)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                _dadosSalvo.value = true
                _cadastroSucesso.value = true

            }
            .addOnFailureListener { error ->
                _cadastroErro.value = error.message.toString()
            }
    }


}