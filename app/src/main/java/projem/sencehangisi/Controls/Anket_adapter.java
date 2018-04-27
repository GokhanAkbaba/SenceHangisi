package projem.sencehangisi.Controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projem.sencehangisi.R;

/**
 * Created by Müslüm BİTGEN on 27.04.2018.
 */

public class Anket_adapter extends RecyclerView.Adapter<Anket_adapter.AnketViewHolder> {
    private Context mContext;
    private ArrayList<AnketInfo> mAnketInfoList;
    public ImageView deneme;
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
        String user_ad=currentItem.getUser_name();
        String user_kulAdi=currentItem.getUser_username();
        String user_img=currentItem.getUser_image();
        String anketSoru=currentItem.getAnket_question();
        String anketImg1=currentItem.getAnket_image1();
        String anketImg2=currentItem.getAnket_image2();

        holder.uAd_soyad.setText(user_ad);
        holder.ukullanici_adi.setText(user_kulAdi);
        holder.anket_soru.setText(anketSoru);
        Picasso.with(mContext).load(user_img).fit().centerInside().into(holder.u_img);
        Picasso.with(mContext).load(anketImg1).fit().centerInside().into(holder.anket_img1);
        Picasso.with(mContext).load(anketImg2).fit().centerInside().into(holder.anket_img2);
    }

    @Override
    public int getItemCount() {
        return mAnketInfoList.size();
    }

    public class AnketViewHolder extends RecyclerView.ViewHolder{
        public TextView uAd_soyad,ukullanici_adi,anket_soru;
        public ImageView u_img,anket_img1,anket_img2;
        public AnketViewHolder(View itemView) {
            super(itemView);
            uAd_soyad=itemView.findViewById(R.id.getName);
            anket_soru=itemView.findViewById(R.id.SoruAnket);
            ukullanici_adi=itemView.findViewById(R.id.getuser);
            u_img=itemView.findViewById(R.id.user_image);
            anket_img1=itemView.findViewById(R.id.anketSecenekFoto1);
            anket_img2=itemView.findViewById(R.id.anketSecenekFoto2);
        }
    }
    public  void getImage(final String url){
        String image_req="req_image";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                       deneme.setImageBitmap(bitmap);

                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        deneme.setImageResource(R.drawable.arka_plan);
                    }
                });
        AppController.getInstance().addToRequestQueue(request,image_req);
    }
}
