package projem.sencehangisi.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciGirisEkrani;

public class AyarlarKullaniciAdi extends AppCompatActivity {
    @BindView(R.id.getUsername) EditText getUsernameTxt;
    TextView deenem;
    private static final String TAG = AyarlarKullaniciAdi.class.getSimpleName();
    private UserInfo userInfo;
private OturumYonetimi userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar_kullanici_adi);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        deenem=(TextView)findViewById(R.id.dene);
        String username = userInfo.getKeyUsername();
        String email    = userInfo.getKeyEmail();
        deenem.setText(email+" "+username);
        getData();
    }
   public void getData(){


       JsonObjectRequest istek=new JsonObjectRequest(Request.Method.GET, WebServisLinkleri.GETDATA_URL,null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           JSONArray jsonArray=response.getJSONArray("users");
                           for (int i=0;i<jsonArray.length();i++){
                               JSONObject users=jsonArray.getJSONObject(i);
                               String usernama=users.getString("kul_adi");
                               getUsernameTxt.setText(usernama);
                           }


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error.printStackTrace();

           }
       });
       AppController.getInstance().addToRequestQueue(istek,TAG);
   }
}
