package com.leon.hobbyapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HobbyDao {
    /* ----- User ----- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUser(vararg user: User)

    @Query("SELECT * FROM user")
    fun selectAllUser(): List<News>

    @Query("SELECT * FROM user WHERE uuid= :id")
    fun selectUser(id:Int): User

    @Delete
    fun deleteUser(user: User)

    @Query ("UPDATE user SET firstName=:firstName, lastName=:lastName, password=:password WHERE uuid=:id")
    fun updateUser(firstName:String, lastName:String, password: String, id: Int)

    @Query("SELECT * FROM user WHERE username=:username AND password=:password")
    fun loginUser(username:String, password:String):User

    /* ----- News ----- */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(vararg news: News)

    @Query("SELECT * FROM news")
    fun selectAllNews(): List<News>

    @Query("SELECT * FROM news WHERE uuid= :id")
    fun selectNews(id:Int): News

    @Delete
    fun deleteNews(news: News)

    @Update
    fun updateNews(news: News)
}