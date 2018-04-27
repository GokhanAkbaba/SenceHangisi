package projem.sencehangisi.Controls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Müslüm BİTGEN on 25.04.2018.
 */

public class AnketInfo {
   private String anket_question,anket_image1,anket_image2,user_name,user_username,user_image;

    public AnketInfo(String anket_question, String anket_image1, String anket_image2, String user_name, String user_username, String user_image) {
        this.anket_question = anket_question;
        this.anket_image1 = anket_image1;
        this.anket_image2 = anket_image2;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_image = user_image;
    }

    public String getAnket_question() {
        return anket_question;
    }

    public String getAnket_image1() {
        return anket_image1;
    }

    public String getAnket_image2() {
        return anket_image2;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_image() {
        return user_image;
    }
}
