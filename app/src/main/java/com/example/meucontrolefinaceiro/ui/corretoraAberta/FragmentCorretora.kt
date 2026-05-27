package com.example.meucontrolefinaceiro.ui.corretoraAberta
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.meucontrolefinaceiro.databinding.FragmentCorretoraBinding

class FragmentCorretora : Fragment() {
    private val ViewModel: CorretoraViewModel by viewModels()
    private val binding by lazy {
        FragmentCorretoraBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}