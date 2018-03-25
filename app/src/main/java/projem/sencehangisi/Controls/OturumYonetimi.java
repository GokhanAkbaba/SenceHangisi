package projem.sencehangisi.Controls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cannet on 12.03.2018.
 */

public class OturumYonetimi {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;


    private static final String PREF_NAME="Login";

    private static final String KEY_IS_LOGGEDIN="isLoggedIn";

    public OturumYonetimi(Context context)
    {
        this._context=context;
        pref=_context.getSharedPreferences(PREF_NAME, _context.MODE_PRIVATE);
        editor=pref.edit();
    }

    public void setLogin(boolean girisYapildi)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,girisYapildi);
        editor.commit();
    }

    public boolean girisYapildi()
    {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
