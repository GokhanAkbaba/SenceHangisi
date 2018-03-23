package projem.sencehangisi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projem.sencehangisi.R;

public class KullaniciKayitSonucEkrani extends Fragment {


    public KullaniciKayitSonucEkrani() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_kullanici_kayit_sonuc_ekrani, container, false);
    }

}
