package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciGirisEkrani;
import projem.sencehangisi.fragments.KullaniciKayitEkrani;
import projem.sencehangisi.fragments.SifremiUnuttum;

public class KullaniciIslemleriActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_kullanici_islemleri);

        KullaniciGirisEkrani kullaniciGirisEkrani=new KullaniciGirisEkrani();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,kullaniciGirisEkrani).commit();

    }
    public void KullaniciKayitAction(View view)
    {
        KullaniciKayitEkrani kullaniciKayitEkrani=new KullaniciKayitEkrani();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,kullaniciKayitEkrani).commit();
    }
    public void SifremiUnuttumAction(View view)
    {

        SifremiUnuttum sifremiUnuttum=new SifremiUnuttum();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,sifremiUnuttum).commit();
    }
}
