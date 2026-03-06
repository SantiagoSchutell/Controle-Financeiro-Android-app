package com.example.meucontrolefinaceiro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.databinding.FragmentBancosBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentBancos : Fragment() {

      private val binding by lazy {
        FragmentBancosBinding.inflate(layoutInflater)
    }

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
    }



    private fun adicionarNovaConta(){

    }

    private fun verificarLogin(){
        /*if (FirebaseAuth.getInstance().currentUser == null){
            findNavController().navigate(R.id.action_fragmentBancos_to_loginFragment2)
        }*/
    }
}