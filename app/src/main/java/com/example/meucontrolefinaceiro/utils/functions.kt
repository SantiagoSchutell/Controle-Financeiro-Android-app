package com.example.meucontrolefinaceiro.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.example.meucontrolefinaceiro.R
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.Locale


fun dobleToReal(valor: Double): String{
        var valorEmBrl = ""
        val ptBrLocale = Locale("pt", "BR")
        val currencyFormatter = NumberFormat.getCurrencyInstance(ptBrLocale)


        if (valor == null){
            return valorEmBrl
        }else{
            val valorformatado  = currencyFormatter.format(valor)
            valorEmBrl = valorformatado.toString()
        }

        return valorEmBrl
    }

fun exibirSnackBar(view: View, mensagem: String){
    Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG).show()
}

fun exibirDialog(context: Context, mensagem: Int, dialog: (Boolean)-> Unit){
    val caixa = AlertDialog.Builder(context)

    caixa.apply {
        setMessage(mensagem)
        setPositiveButton(R.string.confirmar){ a, b->
            dialog(true)
        }
        setNegativeButton(R.string.cancelar){a, b->
            dialog(false)
        }
    }.show()
}

