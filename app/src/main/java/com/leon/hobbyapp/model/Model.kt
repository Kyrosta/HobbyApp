package com.leon.hobbyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/* data class User(
    val id: Int?,
    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
)

data class Hobby(
    val id: Int?,
    val title: String?,
    val createdBy: String?,
    val imageUrl: String?,
    val description: String?,
    val content: String?,
) */

@Entity
data class Hobby(
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="createdBy")
    var createdBy: String,
    @ColumnInfo(name="imageUrl")
    var image:String,
    @ColumnInfo(name="description")
    var desc:String,
    @ColumnInfo(name="content")
    var content:String,
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}

