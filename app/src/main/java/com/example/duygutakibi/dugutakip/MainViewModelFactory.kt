package com.example.duygutakibi.dugutakip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.duygutakibi.veritabani.VeritabaniErisimNesnesi

class MainViewModelFactory (
private val veriKaynagi: VeritabaniErisimNesnesi,
private val uygulama: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(veriKaynagi, uygulama) as T
        }
        throw IllegalArgumentException("Bilinmeyen görüntü model sınıfı")
    }
}