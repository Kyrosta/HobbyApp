package com.leon.hobbyapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leon.hobbyapp.R
import com.leon.hobbyapp.model.TodoDatabase
import com.leon.hobbyapp.view.MainActivity

val DB_NAME = "newtododb"

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null")
    }
}

//val MIGRATION_2_3 = object : Migration(2,3){
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER default 0 not null")
//    }
//}

//val MIGRATION_3_4 = object : Migration(3,4){
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE todo ADD COLUMN todo_date INTEGER default 0 not null")
//    }
//}

fun buildDb(context: Context):TodoDatabase{
    //val db = Room.databaseBuilder(context,TodoDatabase::class.java, DB_NAME).build()
    val db = TodoDatabase.buildDatabase(context)
    return db
}
