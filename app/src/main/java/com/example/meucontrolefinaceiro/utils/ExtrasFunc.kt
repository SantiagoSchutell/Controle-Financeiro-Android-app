package com.example.meucontrolefinaceiro.utils

import android.view.View
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

    fun verificarLoginComId(): String? {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            return null
        } else {
            return userId.toString()
        }
    }

    fun Boolean.toVisibility(): Int = if (this) View.VISIBLE else View.GONE

}