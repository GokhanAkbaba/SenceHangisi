package projem.sencehangisi.Controls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projem.sencehangisi.R;

/**
 * Created by cannet on 30.04.2018.
 */

public class TakipTakipciAdapter extends RecyclerView.Adapter<TakipTakipciAdapter.TakipViewHolder> {
    private Context mContext;
    private ArrayList<TakipTakipciInfo> mTakipTakipciInfoList;

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
        int kul_id=currentItem.getK_id();


        holder.adSoyad.setText(kul_ad);
        holder.kullaniciAdi.setText(kul_kulAdi);
        Picasso.with(mContext).load(kul_img).fit().centerInside().into(holder.kulResmi);
    }

    @Override
    public int getItemCount() {
        return mTakipTakipciInfoList.size();
    }

    public class TakipViewHolder extends RecyclerView.ViewHolder{
        public TextView adSoyad,kullaniciAdi;
        public ImageView kulResmi;
        public TakipViewHolder(View itemView) {
            super(itemView);
            adSoyad=itemView.findViewById(R.id.textAdSoyad);
            kullaniciAdi=itemView.findViewById(R.id.textKulAdi);
            kulResmi=itemView.findViewById(R.id.imgKulResmi);
        }
    }
}
