package projem.sencehangisi.Activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import butterknife.BindView;
import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciKayitEkrani;

public class AnketOlustur extends AppCompatActivity  {

    private static final String TAG = KullaniciKayitEkrani.class.getSimpleName();
    @BindView(R.id.anketSorusuTxt) MultiAutoCompleteTextView anketSorusuTxt;
    @BindView(R.id.anketSecenekFoto1) ImageView anketSecenekFoto1;
    @BindView(R.id.anketSecenekFoto2) ImageView anketSecenekFoto2;
    @BindView(R.id.anketGonderBtn) ImageButton anketGonderBtn;
    private Bitmap bitmap;
    private String file_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anket_olustur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }







    public void anketOlusturKpt(View v)
    {
        finish();
    }

}
