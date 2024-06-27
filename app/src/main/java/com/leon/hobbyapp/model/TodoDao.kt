package com.leon.hobbyapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Hobby)

    @Query("SELECT * FROM hobby")
    fun selectAllHobby(): List<Hobby>

    @Query("SELECT * FROM hobby WHERE uuid= :id")
    fun selectHobby(id:Int): Hobby

    @Delete
    fun deleteHobby(hobby:Hobby)

    @Update
    fun updateHobby(hobby:Hobby)
}