package projem.sencehangisi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class KullaniciIslemleriActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_kullanici_islemleri);

       /* KullaniciKayitEkrani kullaniciKayitEkrani=new KullaniciKayitEkrani();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,kullaniciKayitEkrani).commit();*/

       /* SifremiUnuttum sifremiUnuttum=new SifremiUnuttum();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,sifremiUnuttum).commit();*/

        KullaniciGirisEkrani kullaniciGirisEkrani=new KullaniciGirisEkrani();
        getSupportFragmentManager().beginTransaction().replace(R.id.k_islemleri,kullaniciGirisEkrani).commit();

    }
    public void KullaniciKaydiAction(View view)
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
