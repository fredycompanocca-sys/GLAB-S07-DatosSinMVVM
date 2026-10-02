package com.example.datossinmvvm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovimientoDao {
    @Query("SELECT * FROM Movimiento ORDER BY id DESC")
    suspend fun getAll(): List<Movimiento>

    @Insert
    suspend fun insert(movimiento: Movimiento)

    @Update
    suspend fun update(movimiento: Movimiento)

    @Delete
    suspend fun delete(movimiento: Movimiento)
}