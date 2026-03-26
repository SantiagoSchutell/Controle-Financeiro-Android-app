package com.example.meucontrolefinaceiro.ui.bancos

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
import com.example.meucontrolefinaceiro.databinding.FragmentBancosBinding
import com.example.meucontrolefinaceiro.utils.ExtrasFunc
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FragmentBancos : Fragment() {

    private val binding by lazy {
        FragmentBancosBinding.inflate(layoutInflater)
    }

    private val viewModel: BancosViewModel by viewModels()

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

        if (ExtrasFunc().verificarLogin()) {
            idUsuario = FirebaseAuth.getInstance().currentUser?.uid
        } else {
            findNavController().navigate(R.id.action_fragmentBancos_to_loginFragment2)
        }


        viewModel.erroNome.observe(viewLifecycleOwner) { resId ->
            binding.editTextNomeConta.error = resId?.let { getString(it) }
            binding.progressBarAddBancos.visibility = GONE
        }

        viewModel.erroRadio.observe(viewLifecycleOwner) { resId ->
            binding.progressBarAddBancos.visibility = GONE
            Snackbar.make(
                binding.root,
                resId?.let { getString(it) }.toString(),
                Snackbar.LENGTH_LONG
            ).show()
            binding.radioGroup.setBackgroundColor(R.color.red)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isloading ->
            if (isloading == false) {
                binding.progressBarAddBancos.visibility = GONE
            } else{
                binding.progressBarAddBancos.visibility = VISIBLE
            }
        }

        viewModel.salvarStatus.observe(viewLifecycleOwner) { isSalvo ->
            if (isSalvo == "salvo") {
                binding.AddConta.visibility = GONE
                binding.fabAddConta.visibility = VISIBLE
                binding.outros.visibility = VISIBLE
            } else{
                binding.AddConta.visibility = VISIBLE
                binding.fabAddConta.visibility = GONE
                binding.outros.visibility = GONE
            }
        }


        binding.fabAddConta.setOnClickListener {
            binding.AddConta.visibility = View.VISIBLE
            binding.fabAddConta.visibility = View.GONE
            binding.outros.visibility = View.GONE
        }

        binding.btnCancelar.setOnClickListener {
            binding.AddConta.visibility = View.GONE
            binding.fabAddConta.visibility = View.VISIBLE
            binding.outros.visibility = View.VISIBLE
        }

        binding.btnAdicionar.setOnClickListener {
            val nome = binding.editTextNomeConta.text.toString()
            val isCorrente = binding.radioContaCorrente.isChecked
            val nenhumSelecionado = binding.radioGroup.checkedRadioButtonId == -1

            if (nenhumSelecionado) {
                Snackbar.make(binding.root, R.string.add_error_data, Snackbar.LENGTH_LONG).show()
            } else {
                viewModel.adicionarNovaConta(idUsuario!!, nome, isCorrente)
            }

        }
    }
}