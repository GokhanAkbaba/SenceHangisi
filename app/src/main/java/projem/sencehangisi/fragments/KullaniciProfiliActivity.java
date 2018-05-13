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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Activitys.TakipEdilenActivity;
import projem.sencehangisi.Activitys.TakipcilerActivity;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class KullaniciProfiliActivity extends AppCompatActivity {
    @BindView(R.id.getuser) TextView getUsernameTxt;
    @BindView(R.id.getName) TextView getNameTxt;
    @BindView(R.id.takipEdilenText) TextView takipEdilenText;
    @BindView(R.id.takipciText) TextView takipciText;
    @BindView(R.id.anketText) TextView anketText;
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

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            String adsoyad = extras.getString("Adi");
            String kuladi = extras.getString("KullaniciAdi");
            String foto = extras.getString("resim");
            getNameTxt.setText(adsoyad);
            getUsernameTxt.setText(kuladi);
            getImage(foto);
        }
        TakipEden(userInfo.getKeyId());
        TakipEdilen(userInfo.getKeyId());
        anketSayisi(userInfo.getKeyId());
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

    public void TakipEden(final String userID){
        String tag_string_req = "takipSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipciSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("kullanici");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                takipciText.setText(sayi);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void TakipEdilen(final String userID){
        String tag_string_req = "takipEdilenSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipEdenSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("kullanici");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                takipEdilenText.setText(sayi);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void anketSayisi(final String userID){
        String tag_string_req = "userAnketSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.UserAnketSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("kullaniciAnketleri");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                anketText.setText(sayi);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
}
