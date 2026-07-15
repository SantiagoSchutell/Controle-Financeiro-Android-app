package com.example.meucontrolefinaceiro.ui.banco

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.data.model.Bancos
import com.example.meucontrolefinaceiro.data.repository.AuthRepositoryImp
import com.example.meucontrolefinaceiro.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.io.ByteArrayOutputStream

@HiltViewModel
class BancosViewModel@Inject constructor(private val authRepository: AuthRepositoryImp) : ViewModel() {
    private val idUsuario: String? = authRepository.getUserId()

    private val _erroNome = MutableLiveData<Int?>()
    val erroNome: LiveData<Int?> = _erroNome

    private val _erroRadio = MutableLiveData<Int?>()
    val erroRadio: LiveData<Int?> = _erroRadio

    private val _salvarStatus = MutableLiveData<String?>()
    val salvarStatus: LiveData<String?> = _salvarStatus

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading

    private val _contasList = MutableLiveData<List<Bancos>>()
    val contasList: LiveData<List<Bancos>> = _contasList




    fun adicionarNovaConta(nome : String, isCorrente: Boolean, uri: Uri, context: Context){
        _loading.value = true

            if (nome.isEmpty()){
                _erroNome.value = R.string.add_error_name
                _loading.value = false
                return
            }

            _erroNome.value = null

            //tipo
            val tipoDeConta = if (isCorrente) "contaCorrente" else "contaInvestimentos"


            compactarImagem( nome, tipoDeConta, uri, context )


    }

    fun salvarFirebase(nomeConta: String, tipoDeConta:String, uri: String, context: Context){

        val dadosDaConta = mapOf(
            "nomeConta" to nomeConta,
            "tipoConta" to tipoDeConta,
            "uri" to uri,
            "saldo" to 0,
            "debito" to 0,
            "saldoLiq" to 0
        )


        FirebaseFirestore.getInstance()
            .collection(Constants.USER)
            .document(idUsuario!!)
            .collection(Constants.CONTAS)
            .document()
            .set(dadosDaConta)
            .addOnSuccessListener {
                _loading.value = false
            }
            .addOnFailureListener { error->
                _salvarStatus.value = "erro"
                _loading.value = false

            }

    }

    fun salvarStorage(bytesComprimidos: ByteArray, nomebanco: String, tipoConta:String,  context: Context){
        val storageRef = FirebaseStorage.getInstance()
            .reference.child(idUsuario!!)
            .child(Constants.IMAGENS)
            .child(nomebanco)
            .child(nomebanco)

        storageRef.putBytes(bytesComprimidos)
            .addOnSuccessListener { image->
                storageRef.downloadUrl.addOnSuccessListener {uriDownload->
                    val urlParaSalvarNoBanco = uriDownload.toString()

                    salvarFirebase( nomebanco, tipoConta,urlParaSalvarNoBanco, context )
                    _salvarStatus.value = "salvo"

                    _loading.value = false
                }

            }
            .addOnFailureListener { error->
                _salvarStatus.value = "erro"
                _loading.value = false

            }
    }

    fun compactarImagem(nomeConta: String,tipoConta:String, uri: Uri, context: Context){
          try {
              _loading.value = true
            val inputStream = context.contentResolver.openInputStream(uri)

            val bitMap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitMap == null) {
                _loading.value = false
                _salvarStatus.value = "erro"

            }

            val outputStream = ByteArrayOutputStream()
            bitMap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

             val bytes = outputStream.toByteArray()

              salvarStorage(bytes, nomeConta, tipoConta, context)

        }catch (e: Exception){
            e.printStackTrace()
              _loading.value = false
              _salvarStatus.value = "erro"

          }
    }

    fun obterDados(){
        val ref = FirebaseFirestore.getInstance().collection("usuario")
            .document(idUsuario!!).collection("Contas")

            ref.addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }


                val list = value?.mapNotNull { doc->

                    Bancos(doc.id,
                        doc.getString("nomeConta")?: "Erro",
                        doc.getString("tipoConta")?: "Erro",
                        doc.getString("uri")?: "null"
                    )
                }?: emptyList()

                _contasList.value = list
            }
    }

    fun apagarBanco(banco : Bancos){
        val idBanco = banco.bancoId
        val nomeBanco = banco.bancoNome

        val imagemRef = FirebaseStorage.getInstance()
            .reference
            .child(idUsuario!!)
            .child(Constants.IMAGENS)
            .child(nomeBanco)
            .child(nomeBanco)
        imagemRef.delete()
            .addOnSuccessListener {
                FirebaseFirestore.getInstance()
                    .collection(Constants.USER)
                    .document(idUsuario!!)
                    .collection(Constants.CONTAS)
                    .document(idBanco)
                    .delete()

                    .addOnSuccessListener {
                        Log.i("testeApagar", "Banco Apagado")
                    }
            }
            .addOnFailureListener {erro->
                Log.i("testeApagar", "Erro ao Apagar: ${erro.message}")

            }


    }

}