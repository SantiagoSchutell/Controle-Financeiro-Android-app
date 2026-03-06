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


class LoginFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            if (validarCampos() == true) {
                findNavController().navigate(R.id.action_loginFragment2_to_fragmentBancos)
            }
        }
    }

    private fun validarCampos(): Boolean {
        val email: String = binding.etEmail.text.toString()
        val senha: String = binding.etPassword.text.toString()

        if (email.isNotEmpty()) {
            binding.tilEmail.error = null
        } else {
            binding.tilEmail.error = getString(R.string.login_failed_email)
            return false
        }

        if (senha.isNotEmpty()) {
            binding.tilPassword.error = null
        } else {
            binding.tilPassword.error = getString(R.string.login_failed_password)
            return false
        }


        return true
    }

}