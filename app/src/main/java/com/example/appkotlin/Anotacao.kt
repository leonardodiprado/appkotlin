package com.example.appkotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anotacoes")
data class Anotacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val conteudo: String
)
