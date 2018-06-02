package projem.sencehangisi.Controls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciProfiliActivity;

/**
 * Created by Müslüm BİTGEN on 27.04.2018.
 */

public class Anket_adapter extends RecyclerView.Adapter<Anket_adapter.AnketViewHolder> {
    private Context mContext;
    private ProgressDialog PD;
    private ArrayList<AnketInfo> mAnketInfoList;
    private Takipciİslemleri takipciİslemleri=new Takipciİslemleri();
    public ArrayList<String> oyID=new ArrayList<String>();
    public ArrayList<String> Drm=new ArrayList<String>();
    public Button OySayisi1,OySayisi2,OySayisi3;
    String  cevap1="0",cevap2="0",cevap3="0";
    private static final String TAG = Anket_adapter.class.getSimpleName();
    public Anket_adapter(Context Context, ArrayList<AnketInfo> AnketInfoList) {
        this.mContext = Context;
        this.mAnketInfoList = AnketInfoList;
    }

    public class AnketViewHolder extends RecyclerView.ViewHolder{
        public TextView uAd_soyad,ukullanici_adi,anket_soru,txtDurum,idBilgi,resim1Url,resim2Url,resim3Url,tarih,saat,profilResBilgi,profilKpkBilgi;
        public ImageView u_img,anket_img1,anket_img2,anket_img3,anket_silImg;
        public ImageButton u_oy1,u_oy2,u_oy3;

        boolean deger=false;
        private TextView textView;
        int indis;
        private UserInfo userInfo;
        public AnketViewHolder(View itemView) {
            super(itemView);
            userInfo=new UserInfo(mContext);
            tarih=itemView.findViewById(R.id.tarih);
            saat=itemView.findViewById(R.id.saat);
            uAd_soyad=itemView.findViewById(R.id.getName);
            anket_soru=itemView.findViewById(R.id.SoruAnket);
            ukullanici_adi=itemView.findViewById(R.id.getuser);
            u_img=itemView.findViewById(R.id.user_image);
            anket_img1=itemView.findViewById(R.id.anketSecenekFoto1);
            anket_img2=itemView.findViewById(R.id.anketSecenekFoto2);
            anket_img3=itemView.findViewById(R.id.anketSecenekFoto3);
            anket_silImg=itemView.findViewById(R.id.silSecenek);
            profilResBilgi=itemView.findViewById(R.id.profilResBilgi);
            profilResBilgi.setVisibility(View.INVISIBLE);
            profilKpkBilgi=itemView.findViewById(R.id.profilKpkBilgi);
            profilKpkBilgi.setVisibility(View.INVISIBLE);
            idBilgi=itemView.findViewById(R.id.idBilgi);
            idBilgi.setVisibility(View.INVISIBLE);
            textView=itemView.findViewById(R.id.textView8);
            textView.setVisibility(View.INVISIBLE);
            txtDurum=itemView.findViewById(R.id.btnDurum);
            resim1Url=itemView.findViewById(R.id.resim1Url);
            resim1Url.setVisibility(View.INVISIBLE);
            resim2Url=itemView.findViewById(R.id.resim2Url);
            resim2Url.setVisibility(View.INVISIBLE);
            resim3Url=itemView.findViewById(R.id.resim3Url);
            resim3Url.setVisibility(View.INVISIBLE);
            u_oy1=itemView.findViewById(R.id.oy1);
            u_oy2=itemView.findViewById(R.id.oy2);
            u_oy3=itemView.findViewById(R.id.oy3);
            OySayisi1=itemView.findViewById(R.id.secenekOySayisi1);
            OySayisi2=itemView.findViewById(R.id.secenekOySayisi2);
            OySayisi3=itemView.findViewById(R.id.secenekOySayisi3);
           anket_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog alertDialog = new Dialog(mContext);
                    alertDialog.setContentView(R.layout.resim_buyult);
                    alertDialog.setTitle("Sence Hangisi : Secenek 1");
                    ImageView ımgBuyult=(ImageView) alertDialog.findViewById(R.id.resimBuyult);
                    Picasso.with(mContext).load(resim1Url.getText().toString()).into(ımgBuyult);
                    alertDialog.show();
                }
            });
            anket_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog alertDialog = new Dialog(mContext);
                    alertDialog.setContentView(R.layout.resim_buyult);
                    alertDialog.setTitle("Sence Hangisi : Secenek 2");
                    ImageView ımgBuyult=(ImageView) alertDialog.findViewById(R.id.resimBuyult);
                    Picasso.with(mContext).load(resim2Url.getText().toString()).into(ımgBuyult);
                    alertDialog.show();
                }
            });
            anket_img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog alertDialog = new Dialog(mContext);
                    alertDialog.setContentView(R.layout.resim_buyult);
                    alertDialog.setTitle("Sence Hangisi : Secenek 3");
                    ImageView ımgBuyult=(ImageView) alertDialog.findViewById(R.id.resimBuyult);
                    Picasso.with(mContext).load(resim3Url.getText().toString()).into(ımgBuyult);
                    alertDialog.show();
                }
            });
            anket_silImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(userInfo.getKeyId())==Integer.parseInt(idBilgi.getText().toString()))
                    {
                        final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        builder.setTitle("Sil");
                        builder.setMessage("Gönderi Silinsin mi?");
                        builder.setCancelable(false);
                        builder.setIcon(R.drawable.delete_icon);
                        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("IF"+userInfo.getKeyId()+"sdf"+textView.getText().toString());
                                AnketSil(userInfo.getKeyId(),textView.getText().toString());
                            }
                        });
                        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }
                    else
                    {

                    }
                }
            });
            uAd_soyad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext,KullaniciProfiliActivity.class);
                    i.putExtra("kul_id",idBilgi.getText().toString());
                    i.putExtra("Adi",uAd_soyad.getText().toString());
                    i.putExtra("KullaniciAdi",ukullanici_adi.getText().toString());
                    i.putExtra("resim",profilResBilgi.getText().toString());
                    i.putExtra("kapak_foto",profilKpkBilgi.getText().toString());
                    mContext.startActivity(i);
                }
            });
            ukullanici_adi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext,KullaniciProfiliActivity.class);
                    i.putExtra("kul_id",idBilgi.getText().toString());
                    i.putExtra("Adi",uAd_soyad.getText().toString());
                    i.putExtra("KullaniciAdi",ukullanici_adi.getText().toString());
                    i.putExtra("resim",profilResBilgi.getText().toString());
                    i.putExtra("kapak_foto",profilKpkBilgi.getText().toString());
                    mContext.startActivity(i);
                }
            });
            u_oy1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indis = 0;
                    anketKayit(userInfo.getKeyId(), textView.getText().toString(), indis);
                    if(deger==false)
                    {
                        u_oy1.setImageResource(R.drawable.secenek_dolu_yildiz);
                        deger=true;
                        u_oy2.setEnabled(false);
                        u_oy1.setEnabled(false);
                        u_oy3.setEnabled(false);
                    }
                    OySayisi(textView.getText().toString(),String.valueOf(indis));
                    if(Integer.parseInt(userInfo.getKeyId())!=Integer.parseInt(idBilgi.getText().toString()))
                    {
                        takipciİslemleri.GoneriBildirim(userInfo.getKeyId(),idBilgi.getText().toString(),textView.getText().toString());
                    }
                }
            });
            u_oy2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    indis = 1;
                    anketKayit(userInfo.getKeyId(), textView.getText().toString(), indis);
                    if(deger==false)
                    {
                        u_oy2.setImageResource(R.drawable.secenek_dolu_yildiz);
                        deger=true;
                        u_oy2.setEnabled(false);
                        u_oy1.setEnabled(false);
                        u_oy3.setEnabled(false);
                    }
                    OySayisi(textView.getText().toString(),String.valueOf(indis));
                    if(Integer.parseInt(userInfo.getKeyId())!=Integer.parseInt(idBilgi.getText().toString()))
                    {
                        takipciİslemleri.GoneriBildirim(userInfo.getKeyId(),idBilgi.getText().toString(),textView.getText().toString());
                    }
                }
            });

            u_oy3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    indis = 3;
                    anketKayit(userInfo.getKeyId(), textView.getText().toString(), indis);
                    if(deger==false)
                    {
                        u_oy3.setImageResource(R.drawable.secenek_dolu_yildiz);
                        deger=true;
                        u_oy2.setEnabled(false);
                        u_oy1.setEnabled(false);
                        u_oy3.setEnabled(false);
                    }
                    OySayisi(textView.getText().toString(),String.valueOf(indis));
                    if(Integer.parseInt(userInfo.getKeyId())!=Integer.parseInt(idBilgi.getText().toString()))
                    {
                        takipciİslemleri.GoneriBildirim(userInfo.getKeyId(),idBilgi.getText().toString(),textView.getText().toString());
                    }
                }
            });

        }
    }
    @Override
    public void onBindViewHolder(AnketViewHolder holder, int position) {
        AnketInfo currentItem=mAnketInfoList.get(position);
        String anketID=currentItem.getAnketID();
        String user_ID=currentItem.getAnketKulId();
        String user_ad=currentItem.getUser_name();
        String user_kulAdi=currentItem.getUser_username();
        String user_img=currentItem.getUser_image();
        String anketSoru=currentItem.getAnket_question();
        String anketImg1=currentItem.getAnket_image1();
        String anketImg2=currentItem.getAnket_image2();
        String anketImg3=currentItem.getAnket_image3();
        String tarih=currentItem.getTarih();
        String saat=currentItem.getSaat();
        String user_kapak_foto=currentItem.getUser_kapak_image();
        String btnDrm=currentItem.getBtnDrm();

        SimpleDateFormat sdf = new SimpleDateFormat("H");
        String date = sdf.format(new Date());
        int fark=Integer.parseInt(date)-Integer.parseInt(saat);
        int oy1=currentItem.getOy1();
        int oy2=currentItem.getOy2();
        int oy3=currentItem.getOy3();
        holder.textView.setText(anketID);
        holder.uAd_soyad.setText(user_ad);
        holder.ukullanici_adi.setText(user_kulAdi);
        holder.anket_soru.setText(anketSoru);
        holder.idBilgi.setText(user_ID);
        holder.resim1Url.setText(anketImg1);
        holder.resim2Url.setText(anketImg2);
        holder.resim3Url.setText(anketImg3);
        holder.tarih.setText(tarih);
        holder.saat.setText(fark+" saat önce");
        holder.profilResBilgi.setText(user_img);
        holder.profilKpkBilgi.setText(user_kapak_foto);
        Picasso.with(mContext).load(user_img).fit().centerInside().into(holder.u_img);
        Picasso.with(mContext).load(anketImg1).fit().centerInside().into(holder.anket_img1);
        Picasso.with(mContext).load(anketImg2).fit().centerInside().into(holder.anket_img2);
        Picasso.with(mContext).load(anketImg3).fit().centerInside().into(holder.anket_img3);
        Picasso.with(mContext).load(oy1).fit().centerInside().into(holder.u_oy1);
        Picasso.with(mContext).load(oy2).fit().centerInside().into(holder.u_oy2);
        Picasso.with(mContext).load(oy3).fit().centerInside().into(holder.u_oy3);
        if(btnDrm=="buton1")
        {
            holder.u_oy2.setEnabled(false);
            holder.u_oy1.setEnabled(false);
            holder.u_oy3.setEnabled(false);
           OySayisi(anketID,String.valueOf(0));
        }
       else if(btnDrm=="buton2")
        {
            holder.u_oy2.setEnabled(false);
            holder.u_oy1.setEnabled(false);
            holder.u_oy3.setEnabled(false);
            OySayisi(anketID,String.valueOf(1));

        }
        else if(btnDrm=="buton3")
        {
            holder.u_oy2.setEnabled(false);
            holder.u_oy1.setEnabled(false);
            holder.u_oy3.setEnabled(false);
            OySayisi(anketID,String.valueOf(3));
        }
    }
    @Override
    public AnketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_view,parent,false);
        PD=new ProgressDialog(mContext);
        PD.setCancelable(false);
        return new AnketViewHolder(v);
    }
    @Override
    public int getItemCount() {
        if (mAnketInfoList !=null)
        {
            return mAnketInfoList.size();
        }
        return 0;
    }
    public void OySayisi(final String gonder_id, final String cevap_id){
        String tag_string_req = "anket_oyla";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketOySayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            cevap1="0";
                            cevap2="0";
                            cevap3="0";
                            JSONArray array=jObj.getJSONArray("Oylar");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String cevap=oylar.getString("cevap");
                                String cevapSayisi=oylar.getString("cevapSayisi");
                                if (cevap.equals("0")){
                                    cevap1=cevapSayisi;
                                }
                                else if (cevap.equals("1")){
                                    cevap2=cevapSayisi;
                                }
                                else if (cevap.equals("3")){
                                    cevap3=cevapSayisi;
                                }
                            }
                            System.out.println("Cevap1: "+cevap1+"Cevap2: "+cevap2+"cevap3: "+cevap3);
                            OySayisi1.setText(cevap1);
                            OySayisi2.setText(cevap2);
                            OySayisi3.setText(cevap3);

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

                params.put("gonder_id", gonder_id);
                params.put("cevap_indis", cevap_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void anketKayit(final String userID,final String anketID,final int cevap_indis) {
        String tag_string_req = "anket_oyla";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.AnketOyla_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Anket Oyla: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(mContext, "Tebrikler anket oyladınız!", Toast.LENGTH_LONG).show();

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
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kullanici_id", userID);
                params.put("gonderi_id", anketID);
                params.put("cevap_indis", Integer.toString(cevap_indis));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void AnketSil(final String userID,final String anketID) {
        String tag_string_req = "anket_sil";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.AnketSil_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Anket Sil: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(mContext, "Anketiniz Silinmiştir!", Toast.LENGTH_LONG).show();

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
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kullanici_id", userID);
                params.put("gonderi_id", anketID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void toast(String x){
        Toast.makeText(mContext, x, Toast.LENGTH_SHORT).show();
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
