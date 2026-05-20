package com.example.meucontrolefinaceiro.ui.bancoAberto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.meucontrolefinaceiro.databinding.FragmentFragmentbancoAbertoBinding
import com.google.firebase.auth.FirebaseAuth


class FragmentbancoAberto : Fragment() {
    private val args : FragmentbancoAbertoArgs by navArgs()
    private val viewModel : bancoAbertoViewModel by viewModels()

    private val binding by lazy {
        FragmentFragmentbancoAbertoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idBanco = args.idBanco
        val idusuario = FirebaseAuth.getInstance().currentUser!!.uid



        viewModel.loading.observe(viewLifecycleOwner){status->
            binding.progressBarBancoAberto.visibility = if (status) View.VISIBLE else View.GONE
        }



        ///Trazaçoes
        binding.btnCredito.setOnClickListener {
            val valor = binding.editTextValor.text

            if (valor!!.isEmpty()){
                binding.tilValor.error = "Digite um valor"
            } else{
                viewModel.buscarDados(idusuario, idBanco!!, "credito", valor.toString().toDouble())
            }
        }
        binding.btnDebito.setOnClickListener {
            val valor = binding.editTextValor.text
            if (valor!!.isEmpty()){
                binding.tilValor.error = "Digite um valor"
            } else{
                viewModel.buscarDados(idusuario, idBanco!!, "debito", valor.toString().toDouble())
            }
        }





    }
}