package com.example.meucontrolefinaceiro.ui.bancos

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
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

    private var uriImagem: Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selecionarImagemLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                uriImagem = uri
                binding.imageIco.load(uri)

            } else {
                uriImagem = null
            }
        }


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
                binding.btnAdicionar.visibility = VISIBLE
                binding.btnCancelar.visibility = VISIBLE
            } else{
                binding.progressBarAddBancos.visibility = VISIBLE
                binding.btnAdicionar.visibility = GONE
                binding.btnCancelar.visibility = GONE
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
                if (uriImagem != null){
                    viewModel.adicionarNovaConta(idUsuario!!, nome, isCorrente, uriImagem!! , requireContext())
                } else{
                    Snackbar.make(binding.root, R.string.add_error_img, Snackbar.LENGTH_LONG).show()
                }
            }

        }

        binding.imageIco.setOnClickListener {
            selecionarImagemLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}