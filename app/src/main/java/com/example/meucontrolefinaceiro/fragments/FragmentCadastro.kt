package com.example.meucontrolefinaceiro.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.Data.constantes
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class FragmentCadastro : Fragment() {
    private val binding by lazy {
        FragmentCadastroBinding.inflate(layoutInflater)
    }

    private var userNome: String? = null
    private var userEmail: String? = null
    private var userSenha: String  ? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            if (verificarCampos()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail!!, userSenha!!)
                    .addOnSuccessListener {
                        val userData = mapOf(
                            "UserName" to userNome.toString()
                        )
                        salvarFirebase(FirebaseAuth.getInstance().currentUser!!.uid, userData)
                    }
                    .addOnFailureListener { error->
                        Snackbar.make(requireView(), error.message.toString(), Snackbar.LENGTH_LONG).show()

                    }
            }
        }

        binding.tvBackToLogin.setOnClickListener {
          findNavController().navigate(R.id.action_fragmentCadastroToLogin)
        }
    }


    private fun verificarCampos(): Boolean{
        userNome = binding.textEditName.text.toString()
        userEmail = binding.textEditEmailCadastro.text.toString()
        userSenha = binding.textEditPasswordCadastro.text.toString()

        if (userNome.isNullOrEmpty()){
            binding.textInputName.error = getString(R.string.login_failed_nome)
            return false
        } else{
            binding.textInputName.error = null
            if (userEmail.isNullOrEmpty()){
                binding.textInputEmailCadastro.error = getString(R.string.login_failed_email)
                return false
            } else{
                binding.textInputEmailCadastro.error = null
                if (userSenha.isNullOrEmpty()){
                    binding.textInputPasswordCadastro.error = getString(R.string.login_failed_password)
                    return false
                }else{
                    binding.textInputPasswordCadastro.error = null
                    return true
                }
            }
        }

    }

    private fun salvarFirebase(idUser: String, userInfo: Map<String, String>){
        FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUser)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_fragmentCadastroTobancos)
            }
            .addOnFailureListener { error->
                Log.i("erroFinance", "Erro em salvarFirebase(FragmentCadastro: ${error.message}")
            }
    }

}

