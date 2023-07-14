package com.example.pantrypal4

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.util.Date

class Novo : AppCompatActivity() {
    private val CAMERA_REQUEST_CODE = 100
    private val STORAGE_REQUEST_CODE = 101
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_STORAGE_CODE = 103

    //arrays
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>


    //databse variable
    private lateinit var dbHelper: DataBaseHelper

    private lateinit var pNomeEt: EditText
    private lateinit var pFotoEt: ImageView
    private lateinit var pDatacriadoEt: LocalDate
    private lateinit var pDatadiasEt: EditText
    private lateinit var pLocalEt: Spinner
    private lateinit var pRefeiçaoEt: Spinner
    private lateinit var pQuantidadeEt: EditText
    private lateinit var pDatadeleteEt: LocalDate

    private var nome: String? = ""
    private var local: String? = ""
    private var refeicao: String? = ""
    private var datacriado: LocalDate? = LocalDate.now()
    private var datadias: Number? = 0
    private var quantidade: Number? = 1
    private var datadelete: LocalDate? = null


    private lateinit var pImageView: ImageView
    private lateinit var addRecordButton: Button

    private var imageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo)

        dbHelper = DataBaseHelper(this)

        val spinnerLocal = findViewById<Spinner>(R.id.localAlimento)

        val locais = arrayOf("Geladeira", "Freezer", "Armário")
        val arrayAdp = ArrayAdapter(this@Novo, android.R.layout.simple_spinner_dropdown_item, locais)
        spinnerLocal.adapter = arrayAdp

        val spinnerRefeicao = findViewById<Spinner>(R.id.spinRefeicao)

        val refeicoes = arrayOf("Café da Manhã", "Almoço", "Lanche", "Jantar")
        val arrayAdx = ArrayAdapter(this@Novo, android.R.layout.simple_spinner_dropdown_item, refeicoes)
        spinnerRefeicao.adapter = arrayAdx



        cameraPermission = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        storagePermission = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        pImageView = findViewById(R.id.photoAlimento)
        pImageView.setOnClickListener {
            imagePickDialog()
        }

        addRecordButton = findViewById((R.id.salvar))
        addRecordButton.setOnClickListener {
            inputData()
        }

        pNomeEt = findViewById(R.id.NomeAlimentoNovo)
        //pFotoEt = findViewById(R.id.)
        //pDatacriadoEt = findViewById(R.id.diasC)
        pDatadiasEt = findViewById(R.id.diasC)
        pLocalEt = findViewById(R.id.localAlimento)
        pRefeiçaoEt = findViewById(R.id.spinRefeicao)
        pQuantidadeEt = findViewById(R.id.quantidadeAlimento)
        //pDatadeleteEt = findViewById(R.id.diasC)

    }

    private fun inputData() {

        nome = ""+pNomeEt.text.toString().trim()
        local = ""+pLocalEt.selectedItem.toString().trim()
        refeicao = ""+pRefeiçaoEt.selectedItem.toString().trim()
        datacriado = LocalDate.now()
        datadias = pDatadiasEt.text.toString().toInt()
        quantidade = pQuantidadeEt.text.toString().toInt()
        datadelete =  null



        val timestamp = System.currentTimeMillis()
        val id = dbHelper.insertInfo(
            ""+nome,
            ""+imageUri,
            ""+datacriado,
            ""+datadias,
            ""+local,
            ""+refeicao,
            ""+quantidade,
            ""+datadelete
        )

        Toast.makeText(
            this,
            "Alimento salvo com sucesso",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun imagePickDialog() {
        val options = arrayOf("Camera", "Galery")
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Selecione uma imagem de")
        builder.setItems(options){dialog, which->
            if (which == 0){
                if (checkCameraPermission()){
                    requestCameraPermission()
                }
                else {
                    pickFromCamera()
                }
            }
            else if (which == 1){
                if (!checkStoragePermission()){
                    requestStoragePermission()
                }
                else {
                    pickFromGalery()
                }
            }

        }
        builder.create().show()
     }

    private fun pickFromGalery() {
       val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(
            galleryIntent,
            IMAGE_PICK_STORAGE_CODE
        )
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Nome da foto")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descrição da foto")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(
            cameraIntent,
            IMAGE_PICK_CAMERA_CODE
        )

    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        val resultC = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val resultS = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return resultC && resultS
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            CAMERA_REQUEST_CODE -> {

                if (grantResults.isNotEmpty()){

                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera()
                    }
                    else {
                        Toast.makeText(
                            this,
                            "Necessário permitir acesso a camera e armazenamento",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
            STORAGE_REQUEST_CODE -> {

                if (grantResults.isNotEmpty()){

                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted) {
                        pickFromGalery()
                    }
                    else {
                        Toast.makeText(
                            this,
                            "Necessário permitir acesso ao armazenamento",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }

        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){

        }
        super.onActivityReenter(resultCode, data)
    }
}