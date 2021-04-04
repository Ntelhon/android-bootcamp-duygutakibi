package com.example.duygutakibi.veritabani

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duyguDurumTablosu")
data class DuyguDurum(
    @PrimaryKey(autoGenerate = true)
    var kimlikNumarasi: Long = 0L,
    @ColumnInfo(name = "başlamaMilisaniyesi")
    val baslamaMilisaniyesi: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "bitişMilisaniyesi")
    var bitisMilisaniyesi: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "duyguDurumu")
    var durum: Int = -1

)