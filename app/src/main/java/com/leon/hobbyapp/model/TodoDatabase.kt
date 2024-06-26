package com.leon.hobbyapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leon.hobbyapp.util.DB_NAME
import com.leon.hobbyapp.util.MIGRATION_1_2
import com.leon.hobbyapp.model.TodoDao

@Database(entities = arrayOf(Hobby::class), version = 4)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build()

        operator fun invoke(context: Context){
            if(instance!=null){
                synchronized(LOCK){
                    instance ?: buildDatabase(context).also{
                        instance = it
                    }
                }
            }
        }

    }
}