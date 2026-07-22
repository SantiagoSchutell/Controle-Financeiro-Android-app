package com.example.meucontrolefinaceiro.ui.bancoAberto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.data.model.Tranzacao
import com.example.meucontrolefinaceiro.utils.dobleToReal

class BancoHistoricoAdapter: ListAdapter<Tranzacao, BancoHistoricoAdapter.BancoHistoricoViewHolder>(DiffCallback()) {
    class BancoHistoricoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context
        private val textValor: TextView = itemView.findViewById(R.id.textValorTranzacao)
        private val textDescricao: TextView = itemView.findViewById(R.id.textDescricaoTranzacao)
        private val textTipo: TextView = itemView.findViewById(R.id.textTipoTranzacao)
        private val textData: TextView = itemView.findViewById(R.id.textDataTranzacao)
        private val fundo: ConstraintLayout = itemView.findViewById(R.id.fundo)

        fun bind(item: Tranzacao){
            textValor.text = dobleToReal(item.valorTranzacao)
            textDescricao.text = item.descricao ?: ""
            textTipo.text = item.tipoTranzacao
            textData.text = item.dataTranzacao.toString()

            if (item.tipoTranzacao == "CREDITO"){
                fundo.setBackgroundColor(ContextCompat.getColor(context,R.color.creditoColor))
                textValor.setTextColor(ContextCompat.getColor(context, R.color.verde))
                textTipo.setTextColor(ContextCompat.getColor(context, R.color.verde))
            }else{
                fundo.setBackgroundColor(ContextCompat.getColor(context, R.color.debitoColor))
                textValor.setTextColor(ContextCompat.getColor(context,R.color.red))
                textTipo.setTextColor(ContextCompat.getColor(context,R.color.red))
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BancoHistoricoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tranzacao, parent, false)
        return BancoHistoricoViewHolder(view)
    }
    override fun onBindViewHolder(holder: BancoHistoricoViewHolder, position: Int) {
        val item  = getItem(position)
        holder.bind(item)
    }
    class DiffCallback: DiffUtil.ItemCallback<Tranzacao>(){
        override fun areItemsTheSame(oldItem: Tranzacao, newItem: Tranzacao): Boolean {
            return oldItem.tranzacaoId == newItem.tranzacaoId
        }

        override fun areContentsTheSame(oldItem: Tranzacao, newItem: Tranzacao): Boolean {
            return oldItem == newItem
        }

    }
}