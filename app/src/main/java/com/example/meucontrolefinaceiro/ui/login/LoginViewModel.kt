package com.example.meucontrolefinaceiro.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val _erroEmail = MutableLiveData<Int?>()
    val erroEmail: LiveData<Int?> = _erroEmail

    private val _erroSenha = MutableLiveData<Int?>()
    val erroSenha: LiveData<Int?> = _erroSenha

    private val _loginSucesso = MutableLiveData<Boolean>()
    val loginSucesso: LiveData<Boolean> = _loginSucesso

    private val _messageErro = MutableLiveData<String?>()
    val messageErro: LiveData<String?> = _messageErro

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar



    fun validarCampos(email: String, senha: String): Boolean {
        var camposValidos = true

        if (email.isNullOrEmpty()) {
            _erroEmail.value = R.string.login_failed_email
            camposValidos = false
        } else {
            _erroEmail.value = null
        }

        if (senha.isNullOrEmpty()) {
            _erroSenha.value = R.string.login_failed_password
            camposValidos = false
        } else {
            _erroSenha.value = null
        }

        return camposValidos
    }

    fun fazerLoginNoFirebase(email: String, senha: String){
        _progressBar.value = true

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                _progressBar.value = false
                _loginSucesso.value = true
            }
            .addOnFailureListener {error->
                _progressBar.value = false
                _messageErro.value = error.message.toString()
            }
    }

    fun limparErro(){
        _messageErro.value = null
    }

    fun verificarLogin(): Boolean = FirebaseAuth.getInstance().currentUser != null

}