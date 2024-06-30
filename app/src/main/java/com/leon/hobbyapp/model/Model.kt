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
    data class User(
        @ColumnInfo(name = "username")
        var username: String,
        @ColumnInfo(name = "firstName")
        var firstName: String,
        @ColumnInfo(name = "lastName")
        var lastName: String,
        @ColumnInfo(name = "email")
        var email: String,
        @ColumnInfo(name = "password")
        var password: String
    ) {
        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0
    }

@Entity
data class News(
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="createdBy")
    var createdBy: String,
    @ColumnInfo(name="imageUrl")
    var image:String,
    @ColumnInfo(name="category")
    var category:String,
    @ColumnInfo(name="description")
    var desc:String,
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}

