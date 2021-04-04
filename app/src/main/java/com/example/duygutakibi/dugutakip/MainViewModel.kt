package com.example.duygutakibi.dugutakip

import android.app.Application
import androidx.lifecycle.*
import com.example.duygutakibi.duyguyuHtmleCevir
import com.example.duygutakibi.veritabani.DuyguDurum
import com.example.duygutakibi.veritabani.VeritabaniErisimNesnesi
import kotlinx.coroutines.launch


class MainViewModel(
    val veritabani: VeritabaniErisimNesnesi,
    uygulama: Application
) : AndroidViewModel(uygulama) {
    private var sonDuygu = MutableLiveData<DuyguDurum?>()
    private val duygular = veritabani.duyguVerisiniGetir()

    val duygularString = Transformations.map(duygular) { duygular ->
        duyguyuHtmleCevir(duygular, uygulama.resources)
    }

    private val _duyguDurumaYonlendir = MutableLiveData<DuyguDurum?>()
    val duyguDurumaYonlendir: LiveData<DuyguDurum?>
        get() = _duyguDurumaYonlendir

    private var _snackBarGoster = MutableLiveData<Boolean>()
    val snackBarGoster: LiveData<Boolean>
        get() = _snackBarGoster

    fun snackBarGosterildi() {
        _snackBarGoster.value = false
    }

    fun yonlendirmeTamamlandi() {
        _duyguDurumaYonlendir.value = null
    }

    private suspend fun sonDuyguyuVeritabanindanAl(): DuyguDurum? {
        var duygu = veritabani.sonDuyguyuGetir()

        if (duygu?.baslamaMilisaniyesi != duygu?.bitisMilisaniyesi)
            duygu = null
        return duygu
    }

    private fun sonDuyguyuIlklendir() {
        viewModelScope.launch {
            sonDuygu.value = sonDuyguyuVeritabanindanAl()
        }
    }

    init {
        sonDuyguyuIlklendir()
    }

    fun duyguTakibiniBaslat() {
        viewModelScope.launch {
            val yeniDuygu = DuyguDurum()
            sonDuygu.value = yeniDuygu
            veritabani.ekle(yeniDuygu)
        }
    }

    fun duyguTakibiniDurdur() {
        viewModelScope.launch {
            val snDuygu = veritabani.sonDuyguyuGetir() ?: return@launch

            snDuygu.bitisMilisaniyesi = System.currentTimeMillis()
            veritabani.guncelle(snDuygu)
            _duyguDurumaYonlendir.value = snDuygu
        }
    }

    fun tumVeriyiSil() {
        viewModelScope.launch {
            veritabani.tumVeriyiSil()
            sonDuygu.value = null
            _snackBarGoster.value = true
        }
    }

}