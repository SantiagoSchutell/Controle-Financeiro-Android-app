package com.example.meucontrolefinaceiro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meucontrolefinaceiro.Data.model.Bancos
import com.example.meucontrolefinaceiro.R


class BancosAdapter: ListAdapter<Bancos, BancosAdapter.BancosViewHolder>(DiffCallback()){

    class BancosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val bancoNome: TextView = itemView.findViewById(R.id.textNomebanco)
        private val bancoTipo: TextView = itemView.findViewById(R.id.textTipoConta)
        private val bancoLogo: ImageView = itemView.findViewById(R.id.bancoLogo)

        fun bind(item: Bancos){
                bancoNome.text = item.bancoNome
                bancoTipo.text = item.tipoConta
                bancoLogo.load(item.uriBanco)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BancosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listbancos, parent, false)
        return BancosViewHolder(view)
    }

    override fun onBindViewHolder(holder: BancosViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<Bancos>(){
        override fun areItemsTheSame(
            oldItem: Bancos,
            newItem: Bancos
        ): Boolean {
            return oldItem.bancoId == newItem.bancoId
        }

        override fun areContentsTheSame(
            oldItem: Bancos,
            newItem: Bancos
        ): Boolean {
            return oldItem == newItem
        }

    }

}