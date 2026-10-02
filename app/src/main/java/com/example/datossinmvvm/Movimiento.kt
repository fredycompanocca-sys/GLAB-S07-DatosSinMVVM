package com.example.datossinmvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movimiento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "producto") val producto: String?,
    @ColumnInfo(name = "tipo") val tipo: String?,
    @ColumnInfo(name = "cantidad") val cantidad: Int
)