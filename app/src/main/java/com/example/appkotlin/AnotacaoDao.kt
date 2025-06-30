package com.example.appkotlin

import androidx.room.*

@Dao
interface AnotacaoDao {

    @Query("SELECT * FROM anotacoes")
    fun listarTodas(): List<Anotacao>

    @Insert
    fun inserir(anotacao: Anotacao)

    @Update
    fun atualizar(anotacao: Anotacao)

    @Delete
    fun deletar(anotacao: Anotacao)
}
