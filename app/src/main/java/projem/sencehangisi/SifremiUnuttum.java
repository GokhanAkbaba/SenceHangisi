package projem.sencehangisi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SifremiUnuttum extends Fragment{

    Intent intent=new Intent();
    public SifremiUnuttum() {
        // Required empty public constructor
    }
    /*public void kayit_action(View view)
    {
        intent.setClass(getApplicationContext(),KullaniciKayitEkrani.class);
        startActivity(intent);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sifremi_unuttum, container, false);
    }

}
