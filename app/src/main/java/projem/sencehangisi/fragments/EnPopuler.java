package projem.sencehangisi.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import projem.sencehangisi.Controls.Search.ItemControls;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class EnPopuler extends Fragment {
    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;
    private ProgressDialog PD;
    private ArrayList<AnketInfo>  mInfoArrayList=new ArrayList<>();
    private ArrayList<String>  CvpIndis=new ArrayList<String>();
    private ArrayList<String> GonId=new ArrayList<String>();
    private ArrayList<String> KulId=new ArrayList<String>();
    private UserInfo userInfo;
    private RequestQueue mRequestQueue;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    String btnDrm;
    int oy1,oy2,oy3;
    boolean deger=false;
    int durum=0;
    String kul_ID;

    public EnPopuler() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.fragment_en_populer, container, false);
        PD=new ProgressDialog(this.getContext());
        PD.setCancelable(false);
        mRecyclerView=view.findViewById(R.id.recycler_view_enPopuler);
        mSwipeRefreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh_enPopuler);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAnket_adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemControls(getActivity(), DividerItemDecoration.VERTICAL, 36));
        mRequestQueue= Volley.newRequestQueue(getActivity());
        userInfo=new UserInfo(this.getContext());
        kul_ID=userInfo.getKeyId();

       AnketCek();
       AnketCevapCek(kul_ID);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnketCek();
                        AnketCevapCek(kul_ID);
                    }
                }, 2000);
            }
        });
        return view;

    }
    public void AnketCek(){
        String tag_string_req = "anket_getir";
        PD.setMessage("Yükleniyor..");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.EnPopulerAnketler_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hideDialog();
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("EnPopulerAnketler");
                            mInfoArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String anketID=anket.getString("gonderi_id");
                                String anket_soru=anket.getString("soru");
                                String anketKulId=anket.getString("kul_id");
                                String anket_img1=anket.getString("resim1");
                                String anket_img2=anket.getString("resim2");
                                String anket_img3=anket.getString("resim3");
                                String tarih=anket.getString("tarih");
                                String saat=anket.getString("saat");
                                String kul_adi=anket.getString("kul_adi");
                                String users_kapak_foto=anket.getString("kapak_foto");
                                String ad_soyad=anket.getString("ad_soyad");
                                String kul_resim=anket.getString("kul_image");
                                String anket_takip_kulId=anket.getString("takip_eden_kullanici");
                                oy2= R.drawable.secenek_bos_stil;
                                oy1=R.drawable.secenek_bos_stil;
                                oy3=R.drawable.secenek_bos_stil;
                                for (int j = 0; j < GonId.size(); j++) {
                                    if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 0) && (Integer.parseInt(KulId.get(j)) == Integer.parseInt(anket_takip_kulId))) {
                                        deger = true;
                                        durum = 0;
                                        break;
                                    } else if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 1) && (Integer.parseInt(KulId.get(j)) == Integer.parseInt(anket_takip_kulId))) {
                                        deger = true;
                                        durum = 1;
                                        break;
                                    }
                                    else if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 3) && (Integer.parseInt(KulId.get(j)) == Integer.parseInt(anket_takip_kulId))) {
                                        deger = true;
                                        durum = 3;
                                        break;
                                    }
                                    deger=false;
                                    durum=4;
                                }
                                if (deger == true && durum == 0) {
                                    oy1 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton1";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,tarih,saat,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,anketKulId,users_kapak_foto));
                                    deger = false;
                                    durum = 0;
                                } else if (deger == true && durum == 1) {
                                    oy2 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton2";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,tarih,saat,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,anketKulId,users_kapak_foto));
                                    deger = false;
                                    durum = 1;
                                }
                                else if (deger == true && durum == 3) {
                                    oy3 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton3";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,tarih,saat,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,anketKulId,users_kapak_foto));
                                    deger = false;
                                    durum = 3;
                                }
                                else if(deger==false && durum==4 || GonId.size()==0)
                                {
                                    btnDrm="bos";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,tarih,saat,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,anketKulId,users_kapak_foto));
                                }
                            }
                            mAnket_adapter=new Anket_adapter(getActivity(),mInfoArrayList);
                            mRecyclerView.setAdapter(mAnket_adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);


    }
    public void AnketCevapCek(final String kullanici_id){
        String tag_string_req = "ankat_oyla";
        PD.setMessage("Yükleniyor..");
        showDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketCevapCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hideDialog();
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("AnketlerCevap");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String gonderiID=anket.getString("gonderi_id");
                                String cevap_indis=anket.getString("cevap_indis");
                                String kulID=anket.getString("kullanici_id");
                                CvpIndis.add(cevap_indis);
                                GonId.add(gonderiID);
                                KulId.add(kulID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
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
