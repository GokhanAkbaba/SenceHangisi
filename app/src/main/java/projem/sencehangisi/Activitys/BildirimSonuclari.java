package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import projem.sencehangisi.Controls.Bildirim.BildirimInfo;
import projem.sencehangisi.Controls.Bildirim.Bildirim_Adapter;
import projem.sencehangisi.Controls.Search.ItemControls;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class BildirimSonuclari extends AppCompatActivity {
    private ArrayList<BildirimInfo> mBildirimInfoArrayList=new ArrayList<>();
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private Bildirim_Adapter mBildirim_Adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bildirim_sonuclari);
        mRecyclerView=findViewById(R.id.recycler_view_bildirim);
        mSwipeRefreshLayout= (SwipeRefreshLayout)findViewById(R.id.swiperefresh_bildirim);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mBildirim_Adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemControls(getApplicationContext(), DividerItemDecoration.VERTICAL, 36));
        mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        userInfo=new UserInfo(getApplicationContext());
        BildirimCek(userInfo.getKeyId());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BildirimCek(userInfo.getKeyId());
                    }
                }, 2000);
            }
        });

    }
    public void BildirimCek(final String kullanici_id){
        String tag_string_req = "bildirim_cek";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.BildirimlerCek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("Bildirimler");
                            mBildirimInfoArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject bildirim=jsonArray.getJSONObject(i);
                                String bldKulID=bildirim.getString("kul_id");
                                String bldAdSoyad=bildirim.getString("ad_soyad");
                                String bldKulFoto=bildirim.getString("kul_image");
                                String bldBilgi=bildirim.getString("bilgi");
                                String bldTarih=bildirim.getString("tarih");
                                mBildirimInfoArrayList.add(new BildirimInfo(bldKulID,bldAdSoyad,bldKulFoto,bldBilgi,bldTarih));
                            }
                            mBildirim_Adapter=new Bildirim_Adapter(getApplicationContext(),mBildirimInfoArrayList);
                            mRecyclerView.setAdapter(mBildirim_Adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
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

}
