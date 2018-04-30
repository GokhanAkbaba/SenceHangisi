package projem.sencehangisi.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciGirisEkrani;

public class AyarlarActivity extends AppCompatActivity {
    @BindView(R.id.ayar_username)    TextView getUsernameTxt;
    @BindView(R.id.ayar_email) TextView getEmailTxt;
    @BindView(R.id.ayar_adSoyad) TextView getAdSoyadTxt;
    Unbinder unbinder;
    private UserInfo userInfo;
    private OturumYonetimi userSession;
    Intent intent= new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        unbinder= ButterKnife.bind(this);
        String username = userInfo.getKeyUsername();
        String adSoyad=userInfo.getKeyNAME();
        String email = userInfo.getKeyEmail();
        getUsernameTxt.setText(username);
        getEmailTxt.setText(email);
        getAdSoyadTxt.setText(adSoyad);
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
    public void AdiSoyadiDegistir(View view)
    {
        intent.setClass(getApplicationContext(),AyarlarAdSoyad.class);
        startActivity(intent);
    }
}
