package projem.sencehangisi.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import projem.sencehangisi.R;

public class AyarlarActivity extends AppCompatActivity {

    Intent intent= new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void KullaniciAdiDegistir(View view)
    {
        intent.setClass(getApplicationContext(),AyarlarKullaniciAdi.class);
        startActivity(intent);
    }
    public void SifreDegistir(View view)
    {
        intent.setClass(getApplicationContext(),AyarlarSifre.class);
        startActivity(intent);
    }

    public void EpostaDegistir(View view)
    {
        intent.setClass(getApplicationContext(),AyarlarEposta.class);
        startActivity(intent);
    }
}
