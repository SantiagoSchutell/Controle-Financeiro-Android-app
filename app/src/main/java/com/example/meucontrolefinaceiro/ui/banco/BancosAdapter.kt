package com.example.meucontrolefinaceiro.ui.banco

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.data.model.Bancos

class BancosAdapter(private val onClick: (Bancos) -> Unit): ListAdapter<Bancos, BancosAdapter.BancosViewHolder>(DiffCallback()){

    class BancosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val bancoNome: TextView = itemView.findViewById(R.id.textNomebanco)
        private val bancoTipo: TextView = itemView.findViewById(R.id.textTipoConta)
        private val bancoLogo: ImageView = itemView.findViewById(R.id.bancoLogo)

        private val itemBanco: ConstraintLayout = itemView.findViewById(R.id.itemBanco)

        fun bind(item: Bancos, onClick: (Bancos) -> Unit){
                bancoNome.text = item.bancoNome
                bancoTipo.text = item.tipoConta
                bancoLogo.load(item.uriBanco)
                itemBanco.setOnClickListener {
                    onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BancosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listbancos, parent, false)
        return BancosViewHolder(view)
    }

    override fun onBindViewHolder(holder: BancosViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
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