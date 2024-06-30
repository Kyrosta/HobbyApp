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

val DB_NAME = "newsdb"

fun buildDb(context: Context):HobbyDatabase{
    val db = HobbyDatabase.buildDatabase(context)
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE news ADD COLUMN category VARCHAR(45) DEFAULT '' NOT NULL"
        )
    }
}
