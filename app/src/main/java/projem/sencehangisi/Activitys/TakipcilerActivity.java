package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

public class TakipcilerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TakipTakipciAdapter mTakipTakipciAdapter;
    private ArrayList<TakipTakipciInfo> mInfoArrayList;
    private UserInfo userInfo;
    private RequestQueue mRequestQueue;
    String ID;
    boolean deger=false;
    boolean kontrol=false;
    ArrayList<String> IDler=new ArrayList<String>();
    ArrayList<String> KulIdler=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takipciler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView=findViewById(R.id.recycler_view_takipci);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mInfoArrayList=new ArrayList<>();
        mRequestQueue= Volley.newRequestQueue(this);
        userInfo=new UserInfo(this);
        ID=userInfo.getKeyId();
        TakipciCek(ID);
        TakipEdilenCek(ID);
    }
    public void TakipEdilenCek(final String kullanici_id){
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
                                String  user_id=takip.getString("kul_id");
                                IDler.add(user_id);
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
                params.put("kullanici_id",kullanici_id);
                params.put("takip_eden_id",ID);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void TakipciCek(final String kullanici_id){
        String tag_string_req = "ankat_oyla";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipciCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("Takip");
                            for (int i=0; i < array.length(); i++) {
                                JSONObject takip=array.getJSONObject(i);
                                String user_ad=takip.getString("ad_soyad");
                                String user_kulAdi=takip.getString("kul_adi");
                                String user_img=takip.getString("kul_image");
                                String  user_id=takip.getString("kul_id");
                                for(int j=0;j<IDler.size();j++)
                                {
                                 if(Integer.parseInt(IDler.get(j))==Integer.parseInt(user_id))
                                    {
                                        kontrol=true;
                                        break;
                                    }
                                }
                                if(kontrol==true)
                                {
                                    int tkp_img=R.drawable.checked;
                                    String tkp_drm="isaretli";
                                    mInfoArrayList.add(new TakipTakipciInfo(user_id,user_kulAdi,user_ad,user_img,tkp_img,tkp_drm));
                                    kontrol=false;
                                }
                                else
                                {
                                    int tkp_img=R.drawable.takip_et_img;
                                    String tkp_drm="isaretsiz";
                                    mInfoArrayList.add(new TakipTakipciInfo(user_id,user_kulAdi,user_ad,user_img,tkp_img,tkp_drm));
                                }

                            }
                            mTakipTakipciAdapter=new TakipTakipciAdapter(TakipcilerActivity.this,mInfoArrayList);
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
                params.put("kullanici_id", kullanici_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void anketOlusturKpt(View v)
    {
        finish();
    }

}
