package com.example.duygutakibi.dugutakip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duygutakibi.veritabani.VeritabaniErisimNesnesi

class DuyguSecimViewModelFactory (
    private val duyguId: Long,
    private val veriKaynagi: VeritabaniErisimNesnesi
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DuyguSecimViewModel::class.java)) {
            return DuyguSecimViewModel(duyguId, veriKaynagi) as T
        }

        throw IllegalArgumentException("Bilinmeyen görüntü model sınıfı")
    }
}