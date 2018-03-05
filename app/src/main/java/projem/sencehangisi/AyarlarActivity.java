package projem.sencehangisi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AyarlarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void KullaniciAdiDegisim(View view)
    {
        AyarlarKullaniciAdi ayarlarKullaniciAdi=new AyarlarKullaniciAdi();
        getSupportFragmentManager().beginTransaction().replace(R.id.AyarlarLayout,ayarlarKullaniciAdi).commit();
    }
}
