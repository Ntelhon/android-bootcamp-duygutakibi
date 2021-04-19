package com.example.duygutakibi.dugutakip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duygutakibi.veritabani.VeritabaniErisimNesnesi
import kotlinx.coroutines.launch

class DuyguSecimViewModel(
private val duyguId: Long = 0L,
val veritabani: VeritabaniErisimNesnesi
) : ViewModel()
{
    private val _duyguSecimeYonlendir = MutableLiveData<Boolean?>()

    val duyguSecimeYonlendir: LiveData<Boolean?>
    get() = _duyguSecimeYonlendir

    fun yonlendirmeTamamlandi() {
        _duyguSecimeYonlendir.value = null
    }

    fun duyguDurumSecimiYap(durum: Int) {
        viewModelScope.launch {
            val duygu = veritabani.duyguGetir(duyguId) ?: return@launch
            duygu.durum = durum
            veritabani.guncelle(duygu)

            _duyguSecimeYonlendir.value = true
        }
    }
}