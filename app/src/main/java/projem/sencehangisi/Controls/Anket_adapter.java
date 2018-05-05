package projem.sencehangisi.Controls;

import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.R;

/**
 * Created by Müslüm BİTGEN on 27.04.2018.
 */

public class Anket_adapter extends RecyclerView.Adapter<Anket_adapter.AnketViewHolder> {
    private Context mContext;
    private ArrayList<AnketInfo> mAnketInfoList;
    private ImageButton oy2;
    public Button secenekOySayisi1,secenekOySayisi2;
    private static final String TAG = Anket_adapter.class.getSimpleName();
    public Anket_adapter(Context Context, ArrayList<AnketInfo> AnketInfoList) {
        this.mContext = Context;
        this.mAnketInfoList = AnketInfoList;
    }

    @Override
    public AnketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_view,parent,false);
        return new AnketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnketViewHolder holder, int position) {
        AnketInfo currentItem=mAnketInfoList.get(position);
        String anketID=currentItem.getAnketID();
        String user_ad=currentItem.getUser_name();
        String user_kulAdi=currentItem.getUser_username();
        String user_img=currentItem.getUser_image();
        String anketSoru=currentItem.getAnket_question();
        String anketImg1=currentItem.getAnket_image1();
        String anketImg2=currentItem.getAnket_image2();
        int oy1=currentItem.getOy1();
        int oy2=currentItem.getOy2();

        holder.textView.setText(anketID);
        holder.uAd_soyad.setText(user_ad);
        holder.ukullanici_adi.setText(user_kulAdi);
        holder.anket_soru.setText(anketSoru);
        Picasso.with(mContext).load(user_img).fit().centerInside().into(holder.u_img);
        Picasso.with(mContext).load(anketImg1).fit().centerInside().into(holder.anket_img1);
        Picasso.with(mContext).load(anketImg2).fit().centerInside().into(holder.anket_img2);
        Picasso.with(mContext).load(oy1).fit().centerInside().into(holder.u_oy1);
        Picasso.with(mContext).load(oy2).fit().centerInside().into(holder.u_oy2);

    }

    @Override
    public int getItemCount() {
        return mAnketInfoList.size();
    }

    public class AnketViewHolder extends RecyclerView.ViewHolder{
        public TextView uAd_soyad,ukullanici_adi,anket_soru;
        public ImageView u_img,anket_img1,anket_img2,u_oy2;
        public ImageButton u_oy1;
        public Button secenekOySayisi1,secenekOySayisi2;
        boolean deger=false;
        private TextView textView;
        int indis;
        private UserInfo userInfo;
        public AnketViewHolder(View itemView) {
            super(itemView);
            userInfo=new UserInfo(mContext);
            uAd_soyad=itemView.findViewById(R.id.getName);
            anket_soru=itemView.findViewById(R.id.SoruAnket);
            ukullanici_adi=itemView.findViewById(R.id.getuser);
            u_img=itemView.findViewById(R.id.user_image);
            anket_img1=itemView.findViewById(R.id.anketSecenekFoto1);
            anket_img2=itemView.findViewById(R.id.anketSecenekFoto2);
            textView=itemView.findViewById(R.id.textView8);
            textView.setVisibility(View.INVISIBLE);
            u_oy1=itemView.findViewById(R.id.oy1);
            u_oy2=itemView.findViewById(R.id.oy2);
            secenekOySayisi1=itemView.findViewById(R.id.secenekOySayisi1);
            secenekOySayisi2=itemView.findViewById(R.id.secenekOySayisi2);

                u_oy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        indis = 0;
                        anketKayit(userInfo.getKeyId(), textView.getText().toString(), indis);
                        if(deger==false)
                        {
                            u_oy1.setImageResource(R.drawable.secenek_stil);
                            deger=true;
                            u_oy1.setEnabled(false);
                            u_oy2.setEnabled(false);
                        }
                        OySayisi(textView.getText().toString(),String.valueOf(indis));
                    }
                });
                u_oy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        indis = 1;
                        anketKayit(userInfo.getKeyId(), textView.getText().toString(), indis);
                        if(deger==false)
                        {
                            u_oy2.setImageResource(R.drawable.secenek_stil);
                            deger=true;
                            u_oy2.setEnabled(false);
                            u_oy1.setEnabled(false);
                        }
                        OySayisi(textView.getText().toString(),String.valueOf(indis));
                    }
                });


        }

    }
    public void anketKayit(final String userID,final String anketID,final int cevap_indis) {
        String tag_string_req = "ankat_oyla";
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
    private void OySayisi(final String gonder_id, final String cevap_id){
        String tag_string_req = "ankat_oyla";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.AnketOySayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            //Toast.makeText(mContext,""+gonder_id+" "+cevap_id,Toast.LENGTH_SHORT).show();
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("Oylar");
                            for (int i=0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("Sayi");
                               System.out.println(sayi);
                               Toast.makeText(mContext,""+sayi,Toast.LENGTH_SHORT).show();

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

                params.put("gonder_id", gonder_id);
                params.put("cevap_indis", cevap_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

    }
    private void toast(String x){
        Toast.makeText(mContext, x, Toast.LENGTH_SHORT).show();
    }

}
