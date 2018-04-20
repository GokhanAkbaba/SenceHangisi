package projem.sencehangisi.Activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import projem.sencehangisi.fragments.AnaSayfa;
import projem.sencehangisi.fragments.EnPopuler;

/**
 * Created by cannet on 19.04.2018.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabArray=new String[]{"ANA SAYFA","EN POPÜLER"};
    Integer tabSayi=2;
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabArray[position];
    }
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                AnaSayfa anaSayfa=new AnaSayfa();
                return anaSayfa;

            case 1:
                EnPopuler enPopuler=new EnPopuler();
                return enPopuler;
        }


        return null;
    }

    @Override
    public int getCount() {
        return tabSayi;
    }

}
