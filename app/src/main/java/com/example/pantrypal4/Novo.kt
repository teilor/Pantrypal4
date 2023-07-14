package com.example.pantrypal4

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Novo : AppCompatActivity() {
    private val CAMERA_REQUEST_CODE = 100
    private val STORAGE_REQUEST_CODE = 101
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_STORAGE_CODE = 103

    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    private lateinit var pImageView: ImageView
    private lateinit var addRecordButton: Button

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo)


        val spinnerLocal = findViewById<Spinner>(R.id.localAlimento)

        val locais = arrayOf("Geladeira", "Freezer", "Armário")
        val arrayAdp = ArrayAdapter(this@Novo, android.R.layout.simple_spinner_dropdown_item, locais)
        spinnerLocal.adapter = arrayAdp

        val spinnerRefeicao = findViewById<Spinner>(R.id.spinRefeicao)

        val refeicoes = arrayOf("Café da Manhã", "Almoço", "Lanche", "Jantar")
        val arrayAdx = ArrayAdapter(this@Novo, android.R.layout.simple_spinner_dropdown_item, refeicoes)
        spinnerRefeicao.adapter = arrayAdx

        dbHelper = DataBaseHelper(this)

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
    }

    private fun inputData() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun pickFromCamera() {
        TODO("Not yet implemented")
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
}