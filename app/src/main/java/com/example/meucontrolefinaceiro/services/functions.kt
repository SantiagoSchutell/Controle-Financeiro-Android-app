package com.example.meucontrolefinaceiro.services

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

