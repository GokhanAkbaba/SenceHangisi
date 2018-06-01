package projem.sencehangisi.Controls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private ImageButton takipBtn;
    private UserInfo userInfo;
    private int tkp;
    boolean deger=false;
    Takipciİslemleri takipciİslemleri=new Takipciİslemleri();
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
        int tkpEtImg=currentItem.getTkpEtImg();
        String tkpDurumu=currentItem.getTkpDrm();
        holder.textViewID.setText(kul_id);
        holder.adSoyad.setText(kul_ad);
        holder.kullaniciAdi.setText(kul_kulAdi);
        holder.textTkpDrm.setText(tkpDurumu);
        Picasso.with(mContext).load(kul_img).fit().centerInside().into(holder.kulResmi);
        Picasso.with(mContext).load(tkpEtImg).into(holder.takipBtn);

    }

    @Override
    public int getItemCount() {
        return mTakipTakipciInfoList.size();
    }
    public class TakipViewHolder extends RecyclerView.ViewHolder{
        public TextView adSoyad,kullaniciAdi,textViewID,textTkpDrm;
        public ImageView kulResmi;
        public ImageButton takipBtn;
        public TakipViewHolder(View itemView) {
            super(itemView);
            userInfo=new UserInfo(mContext);
            textViewID=itemView.findViewById(R.id.textView9);
            textViewID.setVisibility(View.INVISIBLE);
            textTkpDrm=itemView.findViewById(R.id.tkpDurmu);
            textTkpDrm.setVisibility(View.INVISIBLE);
            adSoyad=itemView.findViewById(R.id.textAdSoyad);
            kullaniciAdi=itemView.findViewById(R.id.textKulAdi);
            kulResmi=itemView.findViewById(R.id.imgKulResmi);
            takipBtn=itemView.findViewById(R.id.takipciBtn);
           takipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if("isaretli"==textTkpDrm.getText().toString())
                        {
                            if(deger==false)
                            {
                                takipBtn.setImageResource(R.drawable.takip_et_img);
                                takipciİslemleri.TakipciBirak(userInfo.getKeyId(),textViewID.getText().toString());
                                deger=true;
                            }
                            else
                            {
                                takipBtn.setImageResource(R.drawable.checked);
                                takipciİslemleri.TakipciEkle(userInfo.getKeyId(),textViewID.getText().toString());
                                takipciİslemleri.TakipBildirim(userInfo.getKeyId(),textViewID.getText().toString());
                                deger=false;
                            }

                        }
                        else if("isaretsiz"==textTkpDrm.getText().toString())
                        {

                            if(deger==false)
                            {
                                takipBtn.setImageResource(R.drawable.checked);
                                takipciİslemleri.TakipciEkle(userInfo.getKeyId(),textViewID.getText().toString());
                                takipciİslemleri.TakipBildirim(userInfo.getKeyId(),textViewID.getText().toString());
                                deger=true;
                            }
                            else
                            {
                                takipBtn.setImageResource(R.drawable.takip_et_img);
                                takipciİslemleri.TakipciBirak(userInfo.getKeyId(),textViewID.getText().toString());
                                deger=false;
                            }
                        }

                }
            });
        }
    }


}
