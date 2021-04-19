package com.example.duygutakibi.dugutakip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.duygutakibi.R
import com.example.duygutakibi.databinding.FragmentDuyguSecimBinding
import com.example.duygutakibi.veritabani.Veritabani

class DuyguSecimFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val veriBagi: FragmentDuyguSecimBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_duygu_secim, container, false
        )

        val argumanlar = DuyguSecimFragmentArgs.fromBundle(requireArguments())
        val uygulama = requireNotNull(this.activity).application

        val veriKaynagi = Veritabani.ornegiGetir(uygulama).veritabaniErisimNesnesi
        val duyguSecimViewModelFactory =
            DuyguSecimViewModelFactory(argumanlar.duygukimliknumarasi, veriKaynagi)

        val duyguSecimViewModel = ViewModelProvider(
            this,
            duyguSecimViewModelFactory
        ).get(DuyguSecimViewModel::class.java)

        veriBagi.duyguSecimViewModel = duyguSecimViewModel

        duyguSecimViewModel.duyguSecimeYonlendir.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(DuyguSecimFragmentDirections.actionDuyguSecimFragmentToMainFragment())
                duyguSecimViewModel.yonlendirmeTamamlandi()
            }
        })

        return veriBagi.root
    }
}