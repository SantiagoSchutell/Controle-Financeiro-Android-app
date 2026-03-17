package com.example.meucontrolefinaceiro.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.Data.constantes
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentBancosBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FragmentBancos : Fragment() {

      private val binding by lazy {
        FragmentBancosBinding.inflate(layoutInflater)
    }

    private var idUsuario: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verificarLogin()

        binding.fabAddConta.setOnClickListener {
            binding.AddConta.visibility = VISIBLE
            binding.fabAddConta.visibility = GONE
            binding.outros.visibility = GONE
        }

        binding.btnCancelar.setOnClickListener {
            binding.AddConta.visibility = GONE
            binding.fabAddConta.visibility = VISIBLE
            binding.outros.visibility = VISIBLE
        }

        binding.btnAdicionar.setOnClickListener {
            adicionarNovaConta()
        }
    }




    private fun adicionarNovaConta(){
        val nome = binding.editTextNomeConta.text.toString()
        var tipo: String? = null

        fun verificarCampos(): Boolean{
            if (binding.editTextNomeConta.text.isNotEmpty()){
                if (binding.radioGroup.checkedRadioButtonId > -1){
                    return true
                } else{
                    binding.radioGroup.setBackgroundColor(R.color.red)
                    return false
                }
            } else{
                binding.editTextNomeConta.error = getString(R.string.add_error_name)
                return false
            }
        }

        if (verificarCampos() == true){
            val radioGroup = binding.radioGroup
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (binding.radioContaCorrente.isChecked()){
                    tipo = "contaCorrente"
                } else{
                    tipo = "contaInvestimentos"
                }
            }

            val dadosDaConta = mapOf(
                "nomeConta" to nome,
                "tipoConta" to tipo.toString()
            )

            salvarFirebase(idUsuario!!, nome, dadosDaConta)


        }else{
            Snackbar.make(requireView(), getString(R.string.add_error_data), Snackbar.LENGTH_LONG).show()
        }

    }

    private fun verificarLogin(){
        val firebaseAuthVerifi = FirebaseAuth.getInstance().currentUser

        if (firebaseAuthVerifi == null){
            findNavController().navigate(R.id.action_fragmentBancos_to_loginFragment2)
        } else{
            idUsuario = firebaseAuthVerifi.uid.toString()
        }
    }

    private fun salvarFirebase(idUser: String, nomeConta: String, contasInfo: Map<String, String>){
        FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUser)
            .update(nomeConta, contasInfo)
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_fragmentCadastroTobancos)
            }
            .addOnFailureListener { error->
                Log.i("erroFinance", "Erro em salvarFirebase(FragmentCadastro: ${error.message}")
            }
    }
}