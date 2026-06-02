package com.example.meucontrolefinaceiro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.meucontrolefinaceiro.Data.model.Corretora
import com.example.meucontrolefinaceiro.R

class CorretoraAdapter(private var onClick:(Corretora)-> Unit): ListAdapter<Corretora, CorretoraAdapter.CorretoraViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorretoraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ativo, parent, false)
        return CorretoraViewHolder(view)
    }
    override fun onBindViewHolder(holder: CorretoraViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    class CorretoraViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val ativoNome: TextView = itemView.findViewById(R.id.textAtivoName)
        private val ativoSaldo: TextView = itemView.findViewById(R.id.textAtivoSaldo)

        private val ativoClicado: ConstraintLayout = itemView.findViewById(R.id.ativoClicado)

        fun bind(item: Corretora, onClick: (Corretora) -> Unit){
            ativoNome.text = item.ativoNome
            ativoSaldo.text = item.valor

            ativoClicado.setOnClickListener {
                onClick(item)
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Corretora>(){
        override fun areItemsTheSame(oldItem: Corretora, newItem: Corretora): Boolean {
            return oldItem.idAtivo == newItem.idAtivo
        }

        override fun areContentsTheSame(oldItem: Corretora, newItem: Corretora): Boolean {
            return oldItem == newItem
        }

    }

}

