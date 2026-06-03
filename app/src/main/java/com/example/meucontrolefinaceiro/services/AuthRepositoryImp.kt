package com.example.meucontrolefinaceiro.services

import com.google.firebase.auth.FirebaseAuth
import jakarta.inject.Inject

interface AuthRepository{
    fun getUserId(): String?
    fun isUserLogged(): Boolean
    suspend fun logOut()
}

class AuthRepositoryImp@Inject constructor(): AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun getUserId(): String?{
        return firebaseAuth.currentUser?.uid
    }

    override fun isUserLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }
}