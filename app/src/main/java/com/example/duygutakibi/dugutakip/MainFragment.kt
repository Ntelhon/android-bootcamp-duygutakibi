package com.example.duygutakibi.dugutakip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.duygutakibi.R
import com.example.duygutakibi.databinding.MainFragmentBinding
import com.example.duygutakibi.veritabani.Veritabani
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val veriBagi: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )

        val uygulama = requireNotNull(this.activity).application
        val veriKaynagi = Veritabani.ornegiGetir(uygulama).veritabaniErisimNesnesi

        val viewModelFactory = MainViewModelFactory(veriKaynagi, uygulama)

        val mainViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        veriBagi.setLifecycleOwner(this)

        veriBagi.mainViewModel = mainViewModel

        mainViewModel.duyguSecimeYonlendir.observe(viewLifecycleOwner) { duygu ->
            duygu?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDuyguSecimFragment(duygu.kimlikNumarasi)
                )
                mainViewModel.yonlendirmeTamamlandi()
            }
        }

        mainViewModel.snackBarGoster.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true.
                // alternatif olarak toast mesajı da gösterilebilir
                /*
                    Toast.makeText(
                        context,
                        getString(R.string.temizlendi_mesaji),
                        Toast.LENGTH_LONG,
                    ).show()
                    */

                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.temizlendi_mesaji),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                mainViewModel.snackBarGosterildi()
            }
        }

        return veriBagi.root
    }
}
