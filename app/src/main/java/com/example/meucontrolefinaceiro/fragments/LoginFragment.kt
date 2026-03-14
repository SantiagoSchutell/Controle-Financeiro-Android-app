package com.example.meucontrolefinaceiro.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentLoginBinding
import com.google.android.material.search.SearchBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.asDeferred


class LoginFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private var email: String? = null
    private var senha: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null){
            findNavController().navigate(R.id.action_loginFragment2_to_fragmentBancos)
        }

        binding.btnLogin.setOnClickListener {
            if (validarCampos()) {
               FirebaseAuth.getInstance().signInWithEmailAndPassword(email!!, senha!!)
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.action_loginFragment2_to_fragmentBancos)
                    }
                    .addOnFailureListener { error->
                        Snackbar.make(requireView(), error.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
            }
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginToCadastro)
        }
    }

    private fun validarCampos(): Boolean {
        email = binding.etEmail.text.toString()
        senha = binding.etPassword.text.toString()

        if (email.isNullOrEmpty()) {
            binding.tilEmail.error = getString(R.string.login_failed_email)
            return false
        } else {
            binding.tilEmail.error = null
            if (senha.isNullOrEmpty()) {
                binding.tilPassword.error = getString(R.string.login_failed_password)
                return false

            } else {
                binding.tilPassword.error = null
                return true
            }
        }

    }

}