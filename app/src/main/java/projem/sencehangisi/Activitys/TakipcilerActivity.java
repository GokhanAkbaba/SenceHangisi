package projem.sencehangisi.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    boolean deger=false;
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


     parseJson();
    }
    private void parseJson(){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, WebServisLinkleri.TakipciCEK, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("Takip");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject takip=jsonArray.getJSONObject(i);
                                String user_ad=takip.getString("ad_soyad");
                                String user_kulAdi=takip.getString("kul_adi");
                                String user_img=takip.getString("kul_image");
                                String  user_id=takip.getString("kul_id");
                                mInfoArrayList.add(new TakipTakipciInfo(user_id,user_kulAdi,user_ad,user_img));
                            }
                            mTakipTakipciAdapter=new TakipTakipciAdapter(TakipcilerActivity.this,mInfoArrayList);
                            mRecyclerView.setAdapter(mTakipTakipciAdapter);
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
        mRequestQueue.add(request);
    }

    public void TakipEt(View v)
    {

        final ImageButton btn=(ImageButton) findViewById(R.id.takipciBtn);
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
