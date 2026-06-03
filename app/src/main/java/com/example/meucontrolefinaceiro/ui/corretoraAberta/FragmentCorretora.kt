package com.example.meucontrolefinaceiro.ui.corretoraAberta
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.adapters.CorretoraAdapter
import com.example.meucontrolefinaceiro.databinding.FragmentCorretoraBinding
import com.example.meucontrolefinaceiro.utils.exibirDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCorretora : Fragment() {
    private val args: FragmentCorretoraArgs by navArgs()
    private val ViewModel: CorretoraViewModel by viewModels()
    private val binding by lazy {
        FragmentCorretoraBinding.inflate(layoutInflater)
    }

    private var idBanco: String? = null

    private val corretoraAdapter = CorretoraAdapter(
        onClick = {clique-> },
        onLongClique = {clique->
            exibirDialog(requireContext(),
                R.string.apagarAtivoMesnsagem) {status->
                if (status){
                    ViewModel.apagarAtivo(args.idBanco, clique.idAtivo)
                }
            } })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModel.listaAtivos.observe(viewLifecycleOwner){lista->
            corretoraAdapter.submitList(lista)
        }
        binding.recyclerInvestimentos.apply {
            adapter = corretoraAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        ViewModel.erroSalvar.observe(viewLifecycleOwner){error->
          Snackbar.make(requireView(), error?.let { getString(it) }.toString(), Snackbar.LENGTH_LONG).show()
        }
        ViewModel.erroBuscar.observe(viewLifecycleOwner){error->
            if (error!=null){
                Snackbar.make(requireView(), error?.let { getString(it) }.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        ViewModel.loading.observe(viewLifecycleOwner){status->
            binding.progressBar.visibility = if (status) VISIBLE else GONE
        }
        idBanco = args.idBanco

        ViewModel.carregarAtivo( idBanco)


        binding.fabAddInvestimento.setOnClickListener {
            binding.cardAddInvestimento.visibility = VISIBLE
            binding.btnConfirmarInvestimento.setOnClickListener {
                val ativoNome = binding.editTextNomeInvestimento.text.toString()
                ViewModel.salvarNovoAtivo(idBanco, ativoNome )
                binding.cardAddInvestimento.visibility = GONE
            }
        }


    }

}