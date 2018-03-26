package projem.sencehangisi.Controls;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Müslüm BİTGEN on 25.03.2018.
 */

public class UserInfo {
    private static final String TAG = OturumYonetimi.class.getSimpleName();
    private static final String PREF_NAME = "userinfo";
    private static final String KEY_ID = "kul_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SIFRE = "sifre";
    private static final String KEY_RESIM = "resim";
    private static final String KEY_NAME = "ad_soyad";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserInfo(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setId(String id){
        editor.putString(KEY_ID, id);
        editor.apply();
    }
    public void setUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public void setName(String name){
        editor.putString(KEY_NAME, name);
        editor.apply();
    }
    public void setSifre(String sifre){
        editor.putString(KEY_SIFRE, sifre);
        editor.apply();
    }
    public void setResim(String resim){
        editor.putString(KEY_RESIM, resim);
        editor.apply();
    }
    public void clearUserInfo(){
        editor.clear();
        editor.commit();
    }

    public String getKeyUsername(){return prefs.getString(KEY_USERNAME, "");}
    public String getKeyEmail(){return prefs.getString(KEY_EMAIL, "");}
    public String getKeySIFRE(){return prefs.getString(KEY_SIFRE, "");}
    public String getKeyRESIM(){return prefs.getString(KEY_RESIM, "");}
    public String getKeyNAME(){return prefs.getString(KEY_NAME, "");}
    public String getKeyId(){return prefs.getString(KEY_ID, "");}


}

