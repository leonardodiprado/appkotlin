package com.example.appkotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Anotacao::class], version = 1)
abstract class AnotacaoDatabase : RoomDatabase() {

    abstract fun anotacaoDao(): AnotacaoDao

    companion object {
        @Volatile
        private var INSTANCE: AnotacaoDatabase? = null

        fun getDatabase(context: Context): AnotacaoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnotacaoDatabase::class.java,
                    "anotacoes_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Facilita testes para iniciantes, cuidado em apps reais
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
