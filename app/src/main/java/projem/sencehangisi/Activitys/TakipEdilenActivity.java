package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import projem.sencehangisi.R;

public class TakipEdilenActivity extends AppCompatActivity {

    ImageButton btn;
    Boolean deger=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_edilen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn = (ImageButton)findViewById(R.id.takipEdilenBtn);
    }

    public void TakipEdilen(View v)
    {
        if(deger==false)
        {
            btn.setImageResource(R.drawable.plus);
            deger=true;
        }
        else
        {
            btn.setImageResource(R.drawable.checked);
            deger=false;
        }

    }
    public void takipEdilenKpt(View v)
    {
        finish();
    }

}
