package com.example.meucontrolefinaceiro.ui.bancos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meucontrolefinaceiro.R
import com.example.meucontrolefinaceiro.utils.constantes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class BancosViewModel : ViewModel() {

    private val _erroNome = MutableLiveData<Int?>()
    val erroNome: LiveData<Int?> = _erroNome

    private val _erroRadio = MutableLiveData<Int?>()
    val erroRadio: LiveData<Int?> = _erroRadio

    private val _salvarStatus = MutableLiveData<String?>()
    val salvarStatus: LiveData<String?> = _salvarStatus

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading




    fun adicionarNovaConta(userId: String, nome : String, isCorrente: Boolean, uri: Uri, context: Context){
        _loading.value = true

            if (nome.isEmpty()){
                _erroNome.value = R.string.add_error_name
                _loading.value = false
                return
            }

            _erroNome.value = null

            //tipo
            val tipoDeConta = if (isCorrente) "contaCorrente" else "contaInvestimentos"


            compactarImagem(userId, nome, tipoDeConta, uri, context )


    }

    fun salvarFirebase(idUser: String, nomeConta: String, tipoDeConta:String, uri: String, context: Context){

        val dadosDaConta = mapOf(
            "nomeConta" to nomeConta,
            "tipoConta" to tipoDeConta,
            "uri" to uri
        )


        FirebaseFirestore.getInstance()
            .collection(constantes.USER)
            .document(idUser)
            .collection("Contas")
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

    fun salvarStorage(idUser: String, bytesComprimidos: ByteArray, nomebanco: String, tipoConta:String,  context: Context){
        val storageRef = FirebaseStorage.getInstance().reference.child(idUser).child(constantes.IMAGENS).child(nomebanco)

        storageRef.putBytes(bytesComprimidos)

            .addOnSuccessListener { image->
                storageRef.downloadUrl.addOnSuccessListener {uriDownload->
                    val urlParaSalvarNoBanco = uriDownload.toString()

                    salvarFirebase(idUser, nomebanco, tipoConta,urlParaSalvarNoBanco, context )
                    _salvarStatus.value = "salvo"

                    _loading.value = false
                }

            }
            .addOnFailureListener { error->
                _salvarStatus.value = "erro"
                _loading.value = false

            }
    }

    fun compactarImagem(idUser: String, nomeConta: String,tipoConta:String, uri: Uri, context: Context){
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

              salvarStorage(idUser,bytes, nomeConta, tipoConta, context)

        }catch (e: Exception){
            e.printStackTrace()
              _loading.value = false
              _salvarStatus.value = "erro"

          }
    }
}