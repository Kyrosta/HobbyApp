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
import com.leon.hobbyapp.model.HobbyDatabase
import com.leon.hobbyapp.view.MainActivity

val DB_NAME = "hobbyappdb"

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create User table
        database.execSQL("""
            CREATE TABLE User (
                uuid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                username TEXT NOT NULL,
                firstName TEXT NOT NULL, 
                lastName TEXT NOT NULL, 
                email TEXT NOT NULL, 
                password TEXT NOT NULL, 
            )
        """)
        // Create News Table
        database.execSQL("""
            CREATE TABLE News (
                uuid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                title TEXT NOT NULL, 
                createdBy TEXT NOT NULL, 
                image TEXT NOT NULL,
                desc TEXT NOT NULL
            )
        """)
    }
}
fun buildDb(context: Context):HobbyDatabase{
    val db = HobbyDatabase.buildDatabase(context)
    return db
}
