package projem.sencehangisi.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Activitys.TakipEdilenActivity;
import projem.sencehangisi.Activitys.TakipcilerActivity;
import projem.sencehangisi.Controls.AnketInfo;
import projem.sencehangisi.Controls.Anket_adapter;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.Search.ItemControls;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class KullaniciProfiliActivity extends AppCompatActivity {
    @BindView(R.id.getuser) TextView getUsernameTxt;
    @BindView(R.id.getName) TextView getNameTxt;
    @BindView(R.id.takipEdilenText) TextView takipEdilenText;
    @BindView(R.id.takipciText) TextView takipciText;
    @BindView(R.id.anketText) TextView anketText;
    @BindView(R.id.images) CircleImageView mImageView;
    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;
    private RequestQueue mRequestQueue;
    private ArrayList<AnketInfo> mInfoArrayList;
    private ArrayList<String> CvpIndis;
    private ArrayList<String> GonId;
    private ArrayList<String> KulId;
    private UserInfo userInfo;
    private OturumYonetimi userSession;
    String kul_ID;
    String btnDrm;
    int oy1,oy2,oy3;
    boolean deger=false;
    int durum=0;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_kullanici_profili);
        mRecyclerView=findViewById(R.id.recycler_view_profil);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAnket_adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemControls(this, DividerItemDecoration.VERTICAL, 36));
        mRequestQueue= Volley.newRequestQueue(this);
        mInfoArrayList=new ArrayList<>();
        KulId=new ArrayList<String>();
        GonId=new ArrayList<String>();
        CvpIndis=new ArrayList<String>();
       getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

       ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40000000")));

        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        String username = userInfo.getKeyUsername();
        String name = userInfo.getKeyNAME();
        String image=userInfo.getKeyRESIM();
        getNameTxt.setText(name);
        getUsernameTxt.setText(username);
        getImage(image);
        TakipEden(userInfo.getKeyId());
        TakipEdilen(userInfo.getKeyId());
        anketSayisi(userInfo.getKeyId());
        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            String kul_id = extras.getString("kul_id");
            String adsoyad = extras.getString("Adi");
            String kuladi = extras.getString("KullaniciAdi");
            String foto = extras.getString("resim");
            getNameTxt.setText(adsoyad);
            getUsernameTxt.setText(kuladi);
            getImage(foto);
            TakipEden(kul_id);
            TakipEdilen(kul_id);
            anketSayisi(kul_id);
        }
        AnketCek(userInfo.getKeyId());
        AnketCevapCek(userInfo.getKeyId());
    }
    public  void getImage(final String url){
        String image_req="req_image";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.drawable.arka_plan);
                    }
                });
        AppController.getInstance().addToRequestQueue(request,image_req);
    }

    public void TakipciGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipcilerActivity.class);
        startActivity(intent);

    }
    public void TakipEdileniGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipEdilenActivity.class);
        startActivity(intent);
    }
    public void TakipEden(final String userID){
        String tag_string_req = "takipSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipciSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("TakipciSayisi");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                takipciText.setText(sayi);
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

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void TakipEdilen(final String userID){
        String tag_string_req = "takipEdilenSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipEdenSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("TakipEdilenSayisi");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                takipEdilenText.setText(sayi);
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

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void anketSayisi(final String userID){
        String tag_string_req = "userAnketSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.UserAnketSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("KullaniciAnketSayisi");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                anketText.setText(sayi);
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

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void AnketCek(final String kullanici_id){
        String tag_string_req = "anket_getir";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.KullanicininAnketleri_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray jsonArray=jObj.getJSONArray("KullanicininAnketleri");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String anketID=anket.getString("gonderi_id");
                                String user_Id=anket.getString("kullanici_id");
                                String anket_soru=anket.getString("soru");
                                String anket_img1=anket.getString("resim1");
                                String anket_img2=anket.getString("resim2");
                                String anket_img3=anket.getString("resim3");
                                String kul_adi=anket.getString("kul_adi");
                                String ad_soyad=anket.getString("ad_soyad");
                                String kul_resim=anket.getString("kul_image");
                                oy2=R.drawable.secenek_bos_stil;
                                oy1=R.drawable.secenek_bos_stil;
                                oy3=R.drawable.secenek_bos_stil;

                                for (int j = 0; j < GonId.size(); j++) {
                                    if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 0)) {
                                        deger = true;
                                        durum = 0;
                                        break;
                                    } else if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 1)) {
                                        deger = true;
                                        durum = 1;
                                        break;
                                    }
                                    else if ((Integer.parseInt(anketID) == Integer.parseInt(GonId.get(j)))
                                            && (Integer.parseInt(CvpIndis.get(j)) == 3)) {
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
                            mAnket_adapter=new Anket_adapter(KullaniciProfiliActivity.this,mInfoArrayList);
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
