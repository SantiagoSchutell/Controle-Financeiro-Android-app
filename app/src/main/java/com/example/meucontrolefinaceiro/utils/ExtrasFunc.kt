package com.example.meucontrolefinaceiro.utils

import com.google.firebase.auth.FirebaseAuth

class ExtrasFunc {

    fun verificarLogin(): Boolean {
        val firebaseAuthVerifi = FirebaseAuth.getInstance().currentUser
        if (firebaseAuthVerifi == null) {
            return false
        } else {
            return true
        }
    }


}