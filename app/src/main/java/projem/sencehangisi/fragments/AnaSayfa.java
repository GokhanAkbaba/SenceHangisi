package projem.sencehangisi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AnaSayfa extends Fragment {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;

    private ArrayList<AnketInfo> mInfoArrayList=new ArrayList<>();
    private ArrayList<String>  CvpIndis=new ArrayList<String>();
    private ArrayList<String> GonId=new ArrayList<String>();
    private ArrayList<String> KulId=new ArrayList<String>();
    private UserInfo userInfo;
    private RequestQueue mRequestQueue;
    String btnDrm;
    int oy1,oy2,oy3;
    boolean deger=false;
    int durum=0;
    String kul_ID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.fragment_ana_sayfa, container, false);
               mRecyclerView=view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAnket_adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemControls(getActivity(), DividerItemDecoration.VERTICAL, 36));
               mRequestQueue= Volley.newRequestQueue(getActivity());
                userInfo=new UserInfo(this.getContext());
                kul_ID=userInfo.getKeyId();



                        AnketCek(kul_ID);
                        AnketCevapCek(kul_ID);

                return view;
    }
    public void AnketCek(final String kullanici_id){
        String tag_string_req = "anket_getir";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketCEK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("TakipEdilenAnketler");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String anketID=anket.getString("gonderi_id");
                                String anket_soru=anket.getString("soru");
                                String anket_img1=anket.getString("resim1");
                                String anket_img2=anket.getString("resim2");
                                String anket_img3=anket.getString("resim3");
                                String kul_adi=anket.getString("kul_adi");
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
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm));
                                    deger = false;
                                    durum = 0;
                                } else if (deger == true && durum == 1) {
                                    oy2 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton2";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm));
                                    deger = false;
                                    durum = 1;
                                }
                                else if (deger == true && durum == 3) {
                                    oy3 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton3";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm));
                                    deger = false;
                                    durum = 3;
                                }
                                else if(deger==false && durum==4 || GonId.size()==0)
                                {
                                    btnDrm="bos";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm));
                                }
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
