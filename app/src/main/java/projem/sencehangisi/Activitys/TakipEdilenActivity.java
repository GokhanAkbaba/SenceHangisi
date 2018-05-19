package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.TakipTakipciAdapter;
import projem.sencehangisi.Controls.TakipTakipciInfo;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class TakipEdilenActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TakipTakipciAdapter mTakipTakipciAdapter;
    public ArrayList<TakipTakipciInfo> mInfoArrayListTakip=new ArrayList<>();
    private UserInfo userInfo;
    private RequestQueue mRequestQueue;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_edilen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn = (ImageButton)findViewById(R.id.takipEdilenBtn);
        mRecyclerView=findViewById(R.id.recycler_view_takip_edilen);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue= Volley.newRequestQueue(this);
        userInfo=new UserInfo(this);
        String ID=userInfo.getKeyId();
       TakipEdilenlerCek(ID);
    }
    public void TakipEdilenlerCek(final String kullanici_id){
        String tag_string_req = "ankat_takipEdilen";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipEdilenCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("TakipEdilen");
                            for (int i=0; i < array.length(); i++) {
                                JSONObject takip=array.getJSONObject(i);
                                String user_ad=takip.getString("ad_soyad");
                                String user_kulAdi=takip.getString("kul_adi");
                                String user_img=takip.getString("kul_image");
                                String  user_id=takip.getString("kul_id");
                                String tkp_drm="isaretli";
                                int tkp_img=R.drawable.checked;
                                mInfoArrayListTakip.add(new TakipTakipciInfo(user_id,user_kulAdi,user_ad,user_img,tkp_img,tkp_drm));
                            }
                            mTakipTakipciAdapter=new TakipTakipciAdapter(TakipEdilenActivity.this,mInfoArrayListTakip);
                            mRecyclerView.setAdapter(mTakipTakipciAdapter);
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
                params.put("kullanici_id",kullanici_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void takipEdilenKpt(View v)
    {
        finish();
    }

}
