package com.example.datossinmvvm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM User ORDER BY uid DESC LIMIT 1")
    suspend fun getLast(): User?

    @Query("DELETE FROM User WHERE uid = :id")
    suspend fun deleteById(id: Int)
}