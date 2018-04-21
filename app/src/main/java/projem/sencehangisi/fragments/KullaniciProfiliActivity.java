package projem.sencehangisi.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Activitys.TakipEdilenActivity;
import projem.sencehangisi.Activitys.TakipcilerActivity;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.R;

public class KullaniciProfiliActivity extends AppCompatActivity {
    @BindView(R.id.getuser) TextView getUsernameTxt;
    @BindView(R.id.getName) TextView getNameTxt;
    @BindView(R.id.images) CircleImageView mImageView;
    private UserInfo userInfo;
    private OturumYonetimi userSession;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_kullanici_profili);

       getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

       ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40000000")));

        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        String username = userInfo.getKeyUsername();
        String name = userInfo.getKeyNAME();
        String image=userInfo.getKeyRESIM();
        getNameTxt.setText(name);
        getUsernameTxt.setText(username);
        getImage(image);
    }
    public  void getImage(final String url){
        String image_req="req_image";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.drawable.arka_plan);
                    }
                });
        AppController.getInstance().addToRequestQueue(request,image_req);
    }

    public void TakipciGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipcilerActivity.class);
        startActivity(intent);
    }
    public void TakipEdileniGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipEdilenActivity.class);
        startActivity(intent);
    }
}
