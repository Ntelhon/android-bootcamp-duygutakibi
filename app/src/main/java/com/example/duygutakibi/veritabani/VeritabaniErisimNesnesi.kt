package com.example.duygutakibi.veritabani

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VeritabaniErisimNesnesi {
    @Insert
    suspend fun ekle (duyguDurum: DuyguDurum)

    @Query(value = "SELECT * From duyguDurumTablosu Order By kimlikNumarasi Desc")
    fun duyguVerisiniGetir() : LiveData<List<DuyguDurum>>

    @Query(value = "SELECT * From duyguDurumTablosu Where kimlikNumarasi = :kimlik")
    suspend fun duyguGetir(kimlik: Long) : DuyguDurum?

    @Query(value = "SELECT * From duyguDurumTablosu Order By kimlikNumarasi Desc Limit 1")
    suspend fun sonDuyguyuGetir() : DuyguDurum?

    @Update
    suspend fun guncelle(duyguDurum: DuyguDurum)

    @Query(value = "DELETE From duyguDurumTablosu")
    suspend fun tumVeriyiSil()

    @Delete
    suspend fun sil(duyguDurum: DuyguDurum)

}