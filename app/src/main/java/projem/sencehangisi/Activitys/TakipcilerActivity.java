package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import projem.sencehangisi.R;

public class TakipcilerActivity extends AppCompatActivity {
    Boolean deger=false;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takipciler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn = (ImageButton)findViewById(R.id.takipciBtn);

    }
    public void TakipEt(View v)
    {
        if(deger==false)
        {
            btn.setImageResource(R.drawable.checked);
            deger=true;
        }
        else
        {
            btn.setImageResource(R.drawable.plus);
            deger=false;
        }

    }

    public void anketOlusturKpt(View v)
    {
        finish();
    }

}
