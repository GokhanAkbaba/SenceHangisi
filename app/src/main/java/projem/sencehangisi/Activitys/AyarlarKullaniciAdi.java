package projem.sencehangisi.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    @BindView(R.id.newUsername) EditText newUsernameTxt;
    @BindView(R.id.kulAdiUptadeBtn) Button newUsernameBtn;
    private static final String TAG=AyarlarKullaniciAdi.class.getSimpleName();
    private ProgressDialog PD;
    private UserInfo userInfo;
    String username;
    private OturumYonetimi userSession;
    AyarlarActivity ayarlarActivity=new AyarlarActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar_kullanici_adi);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        PD=new ProgressDialog(this);
        PD.setCancelable(false);
        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        username = userInfo.getKeyUsername();
        getUsernameTxt.setText(username);
        final String Id=userInfo.getKeyId();
        newUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameG=newUsernameTxt.getText().toString();
                if(!usernameG.isEmpty())
                {
                    updateUsernama(usernameG,Id);
                }
                else
                {
                    Toast.makeText(AyarlarKullaniciAdi.this,"Lütfen Bigileri Tamamlayınız!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void updateUsernama(final String usernameAd, final String id) {
        String tag_string_req="req_login";
        PD.setMessage("Güncelleniyor..");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.Uusername_URL, new Response.Listener<String>() { @Override
        public void onResponse(String response) {
            Log.d(TAG, "Guncelle Response: " + response.toString());
            hideDialog();
            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");
                if (!error) {
                    toast("Kullanıcı Adı güncellendi!");
                    AyarlarKullaniciAdi.this.finish();
                    AyarlarKullaniciAdi.this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                } else {
                    String errorMsg = jObj.getString("error_msg");
                    toast(errorMsg);
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                toast("Json error: " + e.getMessage());
            }

        }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Guncelleme Hatası: " + error.getMessage());
                toast("Bilinmeyen hata oluştu");
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("kul_adi",usernameAd);
                params.put("kul_id",id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }
    private void showDialog()
    {
        if(!PD.isShowing())
        {
            PD.show();
        }
    }
    private void hideDialog()
    {
        if(PD.isShowing())
        {
            PD.dismiss();
        }
    }
}
