package com.example.meucontrolefinaceiro.ui.bancoAberto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.meucontrolefinaceiro.databinding.FragmentFragmentbancoAbertoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class BancoAbertoFragment : Fragment() {
    private val args: BancoAbertoFragmentArgs by navArgs()
    private val viewModel: BancoAbertoViewModel by viewModels()

    private val binding by lazy {
        FragmentFragmentbancoAbertoBinding.inflate(layoutInflater)
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
        val idBanco = args.idBanco
        val idusuario = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.atualizarDados(idusuario, idBanco!!)

        viewModel.loading.observe(viewLifecycleOwner) { status ->
            binding.progressBarBancoAberto.visibility = if (status) View.VISIBLE else View.GONE
        }
        viewModel.addTrazacaoStatus.observe(viewLifecycleOwner) { status ->
            Snackbar.make(requireView(), getString(status!!), Snackbar.LENGTH_LONG).show()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.erroValorAddTrz.collect { erro ->
                    binding.tilValor.error = erro?.let { getString(it) }
                }
            }
        }
        viewModel.erroValorEdit.observe(viewLifecycleOwner) { error ->
            binding.editSaldo.error = error?.let { getString(it) }
        }


        //atualizar Tela
        viewModel.dadosBanco.observe(viewLifecycleOwner){dados->
            binding.textNomeConta.text = dados.nomeConta
            binding.textSaldoDisponivel.text = dados.saldo
            binding.textDebitos.text = dados.debito
            binding.textSaldoLiq.text = dados.saldoLiq

        }

        ///Trazaçoes
        binding.btnCredito.setOnClickListener {
            val valor = binding.editTextValor.text.toString()
            viewModel.buscarDados(idusuario, idBanco, "credito", valor)
        }

        binding.btnDebito.setOnClickListener {
            val valor = binding.editTextValor.text.toString()
            viewModel.buscarDados(idusuario, idBanco, "debito", valor)
        }

        binding.btnEditSaldo.setOnClickListener {
            binding.btnEditSaldo.visibility = GONE
            binding.btnConfirmarSaldo.visibility = VISIBLE
            binding.editSaldo.visibility = VISIBLE
            binding.textSaldoDisponivel.visibility = GONE
        }

        binding.btnConfirmarSaldo.setOnClickListener {
            val valor = binding.editSaldo.text.toString()
            viewModel.editarSaldo(idusuario, idBanco, valor)

            binding.btnEditSaldo.visibility = VISIBLE
            binding.btnConfirmarSaldo.visibility = GONE
            binding.editSaldo.visibility = GONE
            binding.textSaldoDisponivel.visibility = VISIBLE


        }



    }

}