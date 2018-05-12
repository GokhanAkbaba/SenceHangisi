package projem.sencehangisi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import projem.sencehangisi.Controls.AnketInfo;
import projem.sencehangisi.Controls.Anket_adapter;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;
public class AnaSayfa extends Fragment {
    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;
    private ArrayList<AnketInfo> mInfoArrayList;
    private RequestQueue mRequestQueue;
    String kul_ID;
    int oy1,oy2;
    boolean deger=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.fragment_ana_sayfa, container, false);
             mRecyclerView=view.findViewById(R.id.recycler_view);
               mRecyclerView.setHasFixedSize(true);
               mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mInfoArrayList=new ArrayList<>();
                mRequestQueue= Volley.newRequestQueue(getActivity());
                kul_ID="104";
                AnketCek(kul_ID);
                return view;
    }
    public void AnketCek(final String kullanici_id){
        String tag_string_req = "ankat_oyla";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("Anketler");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String anketID=anket.getString("gonderi_id");
                                String user_ad=anket.getString("ad_soyad");
                                String user_kulAdi=anket.getString("kul_adi");
                                String user_img=anket.getString("kul_image");
                                String anket_soru=anket.getString("soru");
                                String anket_img1=anket.getString("resim1");
                                String anket_img2=anket.getString("resim2");
                                oy2=R.drawable.secenek_bos_stil;
                                oy1=R.drawable.secenek_bos_stil;
                                        mInfoArrayList.add(new AnketInfo(anketID,anket_soru,anket_img1,anket_img2,user_ad,user_kulAdi,user_img,oy1,oy2));
                            }
                            mAnket_adapter=new Anket_adapter(getActivity(),mInfoArrayList);
                            mRecyclerView.setAdapter(mAnket_adapter);
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

    public void AnketCevapCek(final String kullanici_id){
        String tag_string_req = "ankat_oyla";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketCevapCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("AnketlerCevap");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                               String gonderiID=anket.getString("gonderi_id");
                               int cevap_indis=anket.getInt("cevap_indis");
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
                params.put("kullanici_id", kullanici_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

    }

}
