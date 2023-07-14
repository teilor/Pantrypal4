package com.example.pantrypal4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context?):SQLiteOpenHelper (
    context,
    Constants.DB_NAME,
    null,
    Constants.DB_VERSION
        ){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME)
        onCreate(db)
    }

    fun insertInfo(
        nome: String?,
        foto: String?,
        data_criado: String?,
        data_dias: String?,
        local: String?,
        refeicao: String?,
        quantidade: String?,
        data_delete: String?
    ): Long {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Constants.C_NOME, nome)
        values.put(Constants.C_FOTO, foto)
        values.put(Constants.C_DATA_CRIADO, data_criado)
        values.put(Constants.C_DATA_DIAS, data_dias)
        values.put(Constants.C_LOCAL, local)
        values.put(Constants.C_REFEICAO, refeicao)
        values.put(Constants.C_QUANTIDADE, quantidade)
        values.put(Constants.C_DATA_DELETE, data_delete)

        val id = db.insert(Constants.TABLE_NAME, null, values)
        db.close()
        return id
    }

}