package com.example.meucontrolefinaceiro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.meucontrolefinaceiro.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewModel : HomeViewModel by viewModels()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}