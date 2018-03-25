package projem.sencehangisi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import projem.sencehangisi.Activitys.MainActivity;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.R;

public class KullaniciProfiliActivity extends AppCompatActivity {
    private OturumYonetimi oturum;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_kullanici_profili);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }
}
