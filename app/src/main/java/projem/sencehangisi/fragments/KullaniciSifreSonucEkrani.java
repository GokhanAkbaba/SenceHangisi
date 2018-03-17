package projem.sencehangisi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projem.sencehangisi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class KullaniciSifreSonucEkrani extends Fragment {


    public KullaniciSifreSonucEkrani() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kullanici_sifre_sonuc_ekrani, container, false);
    }

}
