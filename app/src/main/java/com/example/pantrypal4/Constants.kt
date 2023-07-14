package com.example.pantrypal4

object Constants {

    //nome db
    const val DB_NAME = "PANTRYPAL_DB"
    const val DB_VERSION = 1

    const val TABLE_NAME = "ALIMENTOS"
    //colunas tabela
    const val C_FOTO = "FOTO"
    const val C_NOME = "NOME"
    const val C_DATA_CRIADO = "DATA_CRIADO"
    const val C_DATA_DIAS = "DATA_DIAS"
    const val C_LOCAL = "LOCAL"
    const val C_REFEICAO = "REFEICAO"
    const val C_QUANTIDADE = "QUANTIDADE"
    const val C_DATA_DELETE = "DATA_DELETE"

    const val CREATE_TABLE = ("CREATE TABLE" + TABLE_NAME + "("
            + C_NOME + " TEXT,"
            + C_FOTO + " TEXT,"
            + C_DATA_CRIADO + " TEXT,"
            + C_DATA_DIAS + " TEXT,"
            + C_LOCAL + " TEXT,"
            + C_REFEICAO + " TEXT,"
            + C_QUANTIDADE + " TEXT,"
            + C_DATA_DELETE + " TEXT,"
            + ");")


}