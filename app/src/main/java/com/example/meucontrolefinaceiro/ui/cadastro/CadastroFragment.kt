package com.example.meucontrolefinaceiro.ui.cadastro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentCadastroBinding
import com.google.android.material.snackbar.Snackbar

class CadastroFragment : Fragment() {
    private val binding by lazy {
        FragmentCadastroBinding.inflate(layoutInflater)
    }

    private val viewModel: CadastroViewModel by viewModels()

    private var idUsuario: String? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nomeErro.observe(viewLifecycleOwner) { resId ->
            binding.textInputName.error = resId?.let { getString(it) }
        }

        viewModel.emailErro.observe(viewLifecycleOwner) { resId ->
            binding.textInputEmailCadastro.error = resId?.let { getString(it) }
        }

        viewModel.userSenha.observe(viewLifecycleOwner) { resId ->
            binding.textInputPasswordCadastro.error = resId?.let { getString(it) }
        }

        viewModel.firebaseErro.observe(viewLifecycleOwner) { erro ->
            Snackbar.make(binding.root, erro.toString(), Snackbar.LENGTH_LONG).show()
        }

        viewModel.cadastroErro.observe(viewLifecycleOwner) { erro ->
            Snackbar.make(binding.root, erro.toString(), Snackbar.LENGTH_LONG).show()
        }

        viewModel.dadosSalvo.observe(viewLifecycleOwner) { isSalvo ->
            if (isSalvo){
                findNavController().navigate(R.id.action_fragmentCadastroTobancos)
            }
        }

        viewModel.cadastroSucesso.observe(viewLifecycleOwner) { isCadastrado ->
            if (isCadastrado){
                findNavController().navigate(R.id.action_fragmentCadastroTobancos)
            }
        }

        binding.btnRegister.setOnClickListener {
            val userNome = binding.textEditName.text.toString()
            val userEmail = binding.textEditEmailCadastro.text.toString()
            val userSenha = binding.textEditPasswordCadastro.text.toString()

            if (viewModel.verificarCampos(userNome, userEmail, userSenha)){
                viewModel.cadastrarUsuario(userNome, userEmail, userSenha)
            }
        }

        binding.tvBackToLogin.setOnClickListener {
          findNavController().navigate(R.id.action_fragmentCadastroToLogin)
        }
    }



}