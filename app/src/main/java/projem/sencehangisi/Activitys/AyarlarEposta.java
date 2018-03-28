package projem.sencehangisi.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AyarlarEposta extends AppCompatActivity {
    @BindView(R.id.eposta) EditText getEpostaTxt;
    @BindView(R.id.newEposta) EditText newpsotaTxt;
    @BindView(R.id.epostaBtnUpdate) Button updateBtn;
    private static final String TAG=AyarlarEposta.class.getSimpleName();
    private ProgressDialog PD;
    private UserInfo userInfo;
    private OturumYonetimi userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar_eposta);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        PD=new ProgressDialog(this);
        PD.setCancelable(false);
        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        userSession=new OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        final String eposta=userInfo.getKeyEmail();
        final String Id=userInfo.getKeyId();
        getEpostaTxt.setText(eposta);
        final String newemail=newpsotaTxt.getText().toString().trim();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epostaUpdateFunc(newemail,Id);
            }
        });
    }
// güncelleme işlemleri hatalı onlar düzeltilecek
    private void epostaUpdateFunc(final  String email,final  String id) {
        String tag_string_req="req_update";
        PD.setMessage("Güncelleniyor..");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.GUNCELLE_URL, new Response.Listener<String>() { @Override
        public void onResponse(String response) {
            Log.d(TAG, "Guncelle Response: " + response.toString());

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");
                hideDialog();
                // Check for error node in json
                if (!error) {

                    AyarlarEposta.this.finish();
                   AyarlarEposta.this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                } else {
                    // Error in login. Get the error message
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
                params.put("email",email);
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
