package projem.sencehangisi.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import projem.sencehangisi.Controls.KullaniciBilgileri;
import projem.sencehangisi.R;

/**
 * Created by cannet on 21.03.2018.
 */

public class NavHeaderMainActivityAdapter
{
    private List<KullaniciBilgileri> kullaniciBilgileriList;
    private Context context;

    private LayoutInflater layoutInflater;

    @BindView(R.id.akb)TextView navHeaderAdSoyadTxt;

    public NavHeaderMainActivityAdapter(Context context, List<KullaniciBilgileri> kullaniciBilgileriList)
    {
        this.context=context;
        this.kullaniciBilgileriList=kullaniciBilgileriList;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return kullaniciBilgileriList.size();
    }
    public boolean isViewFromObject(View view, Object object)
    {
        return view==object;
    }

    public Object instantiateItem(ViewGroup container,int position)
    {
        View view=layoutInflater.inflate(R.layout.nav_header_main,container,false);

        KullaniciBilgileri temp=kullaniciBilgileriList.get(position);
        navHeaderAdSoyadTxt.setText(temp.getAd_soyad());

        container.addView(view);
        return view;

    }
}
