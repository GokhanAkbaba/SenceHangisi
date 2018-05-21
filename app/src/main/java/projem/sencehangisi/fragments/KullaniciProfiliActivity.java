package projem.sencehangisi.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Activitys.AnketOlustur;
import projem.sencehangisi.Activitys.TakipEdilenActivity;
import projem.sencehangisi.Activitys.TakipcilerActivity;
import projem.sencehangisi.Controls.AnketInfo;
import projem.sencehangisi.Controls.Anket_adapter;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.Search.ItemControls;
import projem.sencehangisi.Controls.Takipciİslemleri;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class KullaniciProfiliActivity extends AppCompatActivity {
    private static final String TAG = AnketOlustur.class.getSimpleName();
    @BindView(R.id.getuser) TextView getUsernameTxt;
    @BindView(R.id.getName) TextView getNameTxt;
    @BindView(R.id.takipEdilenText) TextView takipEdilenText;
    @BindView(R.id.takipciText) TextView takipciText;
    @BindView(R.id.anketText) TextView anketText;
    @BindView(R.id.images) CircleImageView mImageView;
    @BindView(R.id.kapakFoto) ImageView kpkFoto;
    @BindView(R.id.fotoDegis) ImageView fotoDegis;
    @BindView(R.id.fotoDegis2) ImageView fotoDegis2;
    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;
    private RequestQueue mRequestQueue;
    String foto;

    private ArrayList<AnketInfo> mInfoArrayList=new ArrayList<>();
    private ArrayList<String> CvpIndis=new ArrayList<String>();
    private ArrayList<String> GonId=new ArrayList<String>();
    private ArrayList<String> KulId=new ArrayList<String>();
    public ArrayList<String> PrfIDler=new ArrayList<String>();
    private Bitmap bitmap,bitmap2,bitmap3,defaults;
    private static final String IMAGE_DIRECTORY = "/Sence Hangisi";
    private int GALLERY = 1, CAMERA = 2;
    private UserInfo userInfo;
    private OturumYonetimi userSession;
    Takipciİslemleri takipciİslemleri=new Takipciİslemleri();
    TakipEdilenActivity takipEdilenActivity=new TakipEdilenActivity();
    public String kul_id,btnDrm;
    int oy1,oy2,oy3,durum=0;
    File f;
    boolean deger=false,kontrol=false,tkpKontrol=false,btnKontrol=false,kpkFotoKontrol=false,prfFotoKontrol=false;
    ImageButton prfTakipEt,prfResDuzenle;
    CircleImageView prfDuzenle;

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
        prfTakipEt=(ImageButton)findViewById(R.id.profilTkpEt);
        prfResDuzenle=(ImageButton)findViewById(R.id.profilResimDüzenle);
        prfDuzenle=(CircleImageView) findViewById(R.id.imagesDuzenle);
        prfResDuzenle.setImageResource(R.drawable.edit);
        prfTakipEt.setVisibility(View.INVISIBLE);
        btnKontrol=false;
        defaults = BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera);
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
        String kapakImage=userInfo.getKeyKapakfoto();
        getNameTxt.setText(name);
        getUsernameTxt.setText(username);
        getImage(image);
        getImageKapak(kapakImage);
        TakipEden(userInfo.getKeyId());
        TakipEdilen(userInfo.getKeyId());
        anketSayisi(userInfo.getKeyId());
        AnketCek(userInfo.getKeyId());
        AnketCevapCek(userInfo.getKeyId());
        TakipEdilenCek(userInfo.getKeyId());
        fotoDegis.setVisibility(View.INVISIBLE);
        fotoDegis2.setVisibility(View.INVISIBLE);
        prfDuzenle.setVisibility(View.INVISIBLE);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog alertDialog = new Dialog(KullaniciProfiliActivity.this);
                alertDialog.setContentView(R.layout.resim_buyult);
                ImageView ımgBuyult=(ImageView) alertDialog.findViewById(R.id.resimBuyult);
                Picasso.with(KullaniciProfiliActivity.this).load(foto).into(ımgBuyult);
                alertDialog.show();
            }
        });
        prfResDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnKontrol==false)
                {
                    fotoDegis.setVisibility(View.VISIBLE);
                    fotoDegis2.setVisibility(View.VISIBLE);
                    prfDuzenle.setVisibility(View.VISIBLE);
                    prfResDuzenle.setImageResource(R.drawable.save);
                    btnKontrol=true;
                }
               else
                {
                    prfDuzenle.setVisibility(View.INVISIBLE);
                    fotoDegis.setVisibility(View.INVISIBLE);
                    fotoDegis2.setVisibility(View.INVISIBLE);
                    prfResDuzenle.setImageResource(R.drawable.edit);
                    btnKontrol=false;
                }
                if(kpkFotoKontrol==true)
                {
                    fotoGuncelle(userInfo.getKeyId(),"1");
                    kpkFotoKontrol=false;
                }
                if(prfFotoKontrol==true)
                {
                    fotoGuncelle(userInfo.getKeyId(),"2");
                    prfFotoKontrol=false;
                }

            }
        });
                if (tkpKontrol == true || tkpKontrol == false) {
                        prfTakipEt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    if (tkpKontrol == true) {
                                        prfTakipEt.setImageResource(R.drawable.takip_et_img);
                                        takipciİslemleri.TakipciBirak(userInfo.getKeyId(), kul_id);
                                        tkpKontrol = false;
                                    } else if (tkpKontrol == false) {
                                        prfTakipEt.setImageResource(R.drawable.checked);
                                        takipciİslemleri.TakipciEkle(userInfo.getKeyId(), kul_id);
                                        tkpKontrol = true;
                                    }
                                }
                        });
                }
    }
    int dgr;
    public void KapakFotoDegis(View v)
    {
        dgr=1;
        showPictureDialog();
        fotoDegis.setVisibility(View.INVISIBLE);
        fotoDegis2.setVisibility(View.INVISIBLE);
        kpkFotoKontrol=true;
    }
    public void ProfilFotoDegis(View v)
    {
        dgr=2;
        showPictureDialog();
        fotoDegis.setVisibility(View.INVISIBLE);
        fotoDegis2.setVisibility(View.INVISIBLE);
        prfDuzenle.setVisibility(View.INVISIBLE);
        prfFotoKontrol=true;
    }
    public void fotoGuncelle(final String userID, final String fotoCst)
    {
        String tag_string_req = "Foto_Degis";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.FotoGuncelle_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        toast("Başarıyla Güncellendi");
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
                Log.e(TAG, "Anket Error: " + error.getMessage());
                toast("Unknown Error occurred");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("kullanici_id", userID);
                params.put("fotoCesit", fotoCst);
                if(fotoCst=="1")
                {
                    if (bitmap == null) {
                        params.put("kapakFoto", getStringImage(defaults));
                    }
                    else
                    {
                        params.put("kapakFoto", getStringImage(bitmap));
                    }
                }
               else if(fotoCst=="2")
                {
                    if(bitmap2 == null)
                    {
                        params.put("profilFoto", getStringImage2(defaults));
                    }
                    else
                    {
                        params.put("profilFoto",getStringImage2(bitmap2));
                    }
                }

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public String getStringImage2(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(KullaniciProfiliActivity.this);
        pictureDialog.setTitle("Resim Seç");
        String[] pictureDialogItems = {
                "Galeriden Fotoğraf Seç",
                "Fotoğraf Çek" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    if(dgr==1)
                    {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        kpkFoto.setImageBitmap(bitmap);
                    }
                    else if(dgr==2)
                    {
                        bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                       mImageView.setImageBitmap(bitmap2);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Resim Seçilemedi", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if(dgr==1) {
                bitmap = (Bitmap) data.getExtras().get("data");
                kpkFoto.setImageBitmap(bitmap);
                saveImage(bitmap);
                Toast.makeText(this, "Fotoğraf Kaydedildi.", Toast.LENGTH_SHORT).show();
            }
                else if(dgr==2)
            {
                bitmap2 = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(bitmap2);
                saveImage(bitmap2);
                Toast.makeText(this, "Fotoğraf Kaydedildi.", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private void toast(String x){
        Toast.makeText(KullaniciProfiliActivity.this, x, Toast.LENGTH_SHORT).show();
    }
    public void TakipEdilenCek(final String kullanici_id){
        final String tag_string_req = "ankat_takipEdilen";
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
                                PrfIDler.add(user_id);
                            }
                            Bundle extras = getIntent().getExtras();
                            if (extras !=null){
                                mInfoArrayList.clear();
                                kul_id = extras.getString("kul_id");
                                System.out.println("asdfasf"+kul_id);
                                String adsoyad = extras.getString("Adi");
                                String kuladi = extras.getString("KullaniciAdi");
                                foto = extras.getString("resim");
                                String kapakFoto=extras.getString("kapak_foto");
                                getNameTxt.setText(adsoyad);
                                getUsernameTxt.setText(kuladi);
                                AnketCek(kul_id);
                                AnketCevapCek(kul_id);
                                for(int k=0;k<PrfIDler.size();k++)
                                {
                                    if(Integer.parseInt(PrfIDler.get(k))==Integer.parseInt(kul_id))
                                    {
                                        kontrol=true;
                                        break;
                                    }
                                }
                                if(kontrol==true)
                                {
                                    prfTakipEt.setImageResource(R.drawable.checked);
                                    kontrol=false;
                                    tkpKontrol=true;
                                    prfTakipEt.setVisibility(View.VISIBLE);
                                    prfResDuzenle.setVisibility(View.INVISIBLE);
                                }
                                else
                                {
                                    prfTakipEt.setImageResource(R.drawable.takip_et_img);
                                    prfTakipEt.setVisibility(View.VISIBLE);
                                    prfResDuzenle.setVisibility(View.INVISIBLE);
                                }
                                if(Integer.parseInt(userInfo.getKeyId())==Integer.parseInt(kul_id))
                                {
                                    prfTakipEt.setVisibility(View.INVISIBLE);
                                    prfResDuzenle.setVisibility(View.VISIBLE);
                                }
                                getImage(foto);
                                getImageKapak(kapakFoto);
                                TakipEden(kul_id);
                                TakipEdilen(kul_id);
                                anketSayisi(kul_id);

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
                params.put("takip_eden_id",kullanici_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
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
    public  void getImageKapak(final String url){
        String image_req="req_image";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        kpkFoto.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        kpkFoto.setImageResource(R.drawable.arka_plan);
                    }
                });
        AppController.getInstance().addToRequestQueue(request,image_req);
    }
    public void TakipciGoster(View view) {
        Intent intent=new Intent(getApplicationContext(), TakipcilerActivity.class);
        intent.putExtra("kul_id",kul_id);
        startActivity(intent);

    }
    public void TakipEdileniGoster(View view) {

            Intent intent=new Intent(getApplicationContext(), TakipEdilenActivity.class);
            intent.putExtra("kul_id",kul_id);
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
                                String user_kapak_foto=anket.getString("kapak_foto");
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
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,user_Id,user_kapak_foto));
                                    deger = false;
                                    durum = 0;
                                } else if (deger == true && durum == 1) {
                                    oy2 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton2";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,user_Id,user_kapak_foto));
                                    deger = false;
                                    durum = 1;
                                }
                                else if (deger == true && durum == 3) {
                                    oy3 = R.drawable.secenek_dolu_yildiz;
                                    btnDrm="buton3";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,user_Id,user_kapak_foto));
                                    deger = false;
                                    durum = 3;
                                }
                                else if(deger==false && durum==4 || GonId.size()==0)
                                {
                                    btnDrm="bos";
                                    mInfoArrayList.add(new AnketInfo(anketID, anket_soru, anket_img1, anket_img2,anket_img3,oy1,oy2,oy3,kul_resim,ad_soyad,kul_adi,btnDrm,user_Id,user_kapak_foto));
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
