package com.example.meucontrolefinaceiro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val _totalSaldo = MutableLiveData<String?>()
    val totalSaldo: LiveData<String?> = _totalSaldo

    private val _totalDebitos = MutableLiveData<String?>()
    val totalDebitos: LiveData<String?> = _totalDebitos

    private val _totalSaldoConta = MutableLiveData<String?>()
    val totalSaldoConta: LiveData<String?> = _totalSaldoConta

    private val _totalInvestido = MutableLiveData<String?>()
    val totalInvestido: LiveData<String?> = _totalInvestido

    private val _erroBuscar = MutableLiveData<Int?>()
    val erroBuscar: LiveData<Int?> = _erroBuscar

    private val _erroLogin = MutableLiveData<Int?>()
    val erroLogin: LiveData<Int?> = _erroLogin

    private fun buscarSaldos(){
        val idUsuario: String? = FirebaseAuth.getInstance().currentUser?.uid

        if (idUsuario != null){

            ///Vou tacar tudo em SQLite assim que eu enctro ou crio uma conta nova e deois so carregar aqui
            val ref = FirebaseFirestore.getInstance()
                .collection(Constants.USER)
                .document(idUsuario)

        }else{
            _erroLogin.value = R.string.login_subtitle
        }
    }

}