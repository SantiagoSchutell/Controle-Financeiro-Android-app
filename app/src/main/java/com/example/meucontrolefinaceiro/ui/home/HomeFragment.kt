package com.example.meucontrolefinaceiro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HomeFragment() : Fragment() {
    private val viewModel : HomeViewModel by viewModels()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Tirar depois
        if (FirebaseAuth.getInstance().currentUser?.uid==null){
            findNavController().navigate(R.id.action_fragmentHome_to_loginFragment2)
        }


        viewModel.buscarSaldos()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resumoParaTela.collect { resumo->
                if (resumo!=null){

                    binding.textDebitosTotal.text = resumo.contaDebitoString
                    binding.textSaldoEmContaTotal.text = resumo.contaSaldoString

                    binding.textSaldoTotalInvestido.text = resumo.corretoraSaldoString


                    //Valor Total
                    binding.textTotal.text = resumo.totalSaldos
                }
            }
        }




        binding.btnContas.setOnClickListener {
           findNavController().navigate(R.id.action_fragmentHomeToBancos)
        }

    }
}