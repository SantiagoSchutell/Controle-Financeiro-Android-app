package com.example.meucontrolefinaceiro.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
class LoginFragment : Fragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.verificarLogin()){
            findNavController().navigate(R.id.action_loginFragment2_to_fragmentBancos)
        }

        viewModel.loginSucesso.observe(viewLifecycleOwner){sucesso->
            if (sucesso) {
                    findNavController().navigate(R.id.action_loginFragment2_to_fragmentBancos)
                }
        }

        viewModel.messageErro.observe(viewLifecycleOwner){mensagem->
            mensagem?.let{
                Snackbar.make(binding.root,mensagem.toString(), Snackbar.LENGTH_LONG ).show()
                viewModel.limparErro()
            }
        }

        viewModel.erroEmail.observe(viewLifecycleOwner) { resId ->
            binding.tilEmail.error = resId?.let { getString(it) }
        }

        viewModel.erroSenha.observe(viewLifecycleOwner) { resId ->
            binding.tilPassword.error = resId?.let { getString(it) }
        }

        viewModel.progressBar.observe(viewLifecycleOwner){isLoading->
            if (isLoading == true){
                binding.progressBarLogin.visibility = VISIBLE
                binding.btnLogin.visibility = GONE
            }else{
                binding.progressBarLogin.visibility = GONE
                binding.btnLogin.visibility = VISIBLE
            }
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val senha = binding.etPassword.text.toString()

            if (viewModel.validarCampos(email, senha)) {
                viewModel.fazerLoginNoFirebase(email, senha)
            }
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginToCadastro)
        }

    }
}