package projem.sencehangisi.Controls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.R;

/**
 * Created by cannet on 30.04.2018.
 */

public class TakipTakipciAdapter extends RecyclerView.Adapter<TakipTakipciAdapter.TakipViewHolder> {
    private Context mContext;
    private ArrayList<TakipTakipciInfo> mTakipTakipciInfoList;
    private ImageButton takipBtn;
    private UserInfo userInfo;
    private static final String TAG = Anket_adapter.class.getSimpleName();
    public TakipTakipciAdapter(Context Context, ArrayList<TakipTakipciInfo> TakipInfoList) {
        this.mContext = Context;
        this.mTakipTakipciInfoList = TakipInfoList;
    }

    @Override
    public TakipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.content_takipciler,parent,false);
        return new TakipViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TakipViewHolder holder, int position) {
        TakipTakipciInfo currentItem= mTakipTakipciInfoList.get(position);
        String kul_ad=currentItem.getK_adSoyad();
        String kul_kulAdi=currentItem.getK_adi();
        String kul_img=currentItem.getK_resmi();
        String kul_id=currentItem.getK_id();
        holder.textViewID.setText(kul_id);
        holder.adSoyad.setText(kul_ad);
        holder.kullaniciAdi.setText(kul_kulAdi);
        Picasso.with(mContext).load(kul_img).fit().centerInside().into(holder.kulResmi);
    }

    @Override
    public int getItemCount() {
        return mTakipTakipciInfoList.size();
    }

    public class TakipViewHolder extends RecyclerView.ViewHolder{
        public TextView adSoyad,kullaniciAdi,textViewID;
        public ImageView kulResmi;
        public TakipViewHolder(View itemView) {
            super(itemView);
            userInfo=new UserInfo(mContext);
            textViewID=itemView.findViewById(R.id.textView9);
            textViewID.setVisibility(View.INVISIBLE);
            adSoyad=itemView.findViewById(R.id.textAdSoyad);
            kullaniciAdi=itemView.findViewById(R.id.textKulAdi);
            kulResmi=itemView.findViewById(R.id.imgKulResmi);
            takipBtn=itemView.findViewById(R.id.takipciBtn);
            takipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   TakipciEkle(userInfo.getKeyId(),textViewID.getText().toString());
                }
            });
        }
    }
    public void TakipciEkle(final String userID,final String takipciID) {
        String tag_string_req = "takipci_ekle";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.TakipciEkle, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Anket Oyla: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(mContext, "Tebrikler Takip ediyorsunuz!", Toast.LENGTH_LONG).show();

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

                params.put("takip_eden_kullanici", userID);
                params.put("takip_edilen_kullanici", takipciID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void toast(String x){
        Toast.makeText(mContext, x, Toast.LENGTH_SHORT).show();
    }
}
