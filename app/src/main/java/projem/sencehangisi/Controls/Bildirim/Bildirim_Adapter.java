package projem.sencehangisi.Controls.Bildirim;

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
 * Created by cannet on 02.06.2018.
 */

public class Bildirim_Adapter  extends RecyclerView.Adapter<Bildirim_Adapter.BildirimViewHolder> {
    private Context mContext;
    private ArrayList<BildirimInfo> mBildirimInfoList;
    public Bildirim_Adapter(Context Context, ArrayList<BildirimInfo> BildirimInfoList) {
        this.mContext = Context;
        this.mBildirimInfoList = BildirimInfoList;
    }

    @Override
    public Bildirim_Adapter.BildirimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_view_bildirim,parent,false);
        return new Bildirim_Adapter.BildirimViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Bildirim_Adapter.BildirimViewHolder holder, int position) {
        BildirimInfo currentItem= mBildirimInfoList.get(position);
        String bildirimBilgi=currentItem.getBilgi();
        String kul_adSoyad=currentItem.getAd_soyad();
        String kul_img=currentItem.getKul_image();
        String kul_id=currentItem.getKul_id();
        String bildirimTarih=currentItem.getTarih();
        holder.bildirimKulId.setText(kul_id);
        holder.bildirimAdSoyad.setText(kul_adSoyad);
        holder.bildirimBilgi.setText(bildirimBilgi);
        holder.bildirimTarih.setText(bildirimTarih);
        Picasso.with(mContext).load(kul_img).fit().centerInside().into(holder.bildirimKulResmi);

    }

    @Override
    public int getItemCount() {
        return mBildirimInfoList.size();
    }
    public class BildirimViewHolder extends RecyclerView.ViewHolder {
        public TextView bildirimAdSoyad, bildirimBilgi, bildirimKulId,bildirimTarih;
        public ImageView bildirimKulResmi;

        public BildirimViewHolder(View itemView) {
            super(itemView);
            bildirimKulId = itemView.findViewById(R.id.bildirim_kulId);
            bildirimKulId.setVisibility(View.INVISIBLE);
            bildirimAdSoyad = itemView.findViewById(R.id.bildirim_adSoyad);
            bildirimBilgi = itemView.findViewById(R.id.bildirim_bilgi);
            bildirimKulResmi = itemView.findViewById(R.id.bildirim_kul_foto);
            bildirimTarih=itemView.findViewById(R.id.bildirim_tarih);

        }
    }}

