package projem.sencehangisi.Controls;

/**
 * Created by Müslüm BİTGEN on 25.04.2018.
 */

public class AnketInfo {
     private String anketID;
    private String anket_question;
    private String anket_image1;
    private String anket_image2;
    private String anket_image3;
    private String user_name;
    private String user_username;
    private String user_image;
    private String btnDrm;
    private String anketKulId;
    private int oy1,oy2,oy3;

    public AnketInfo(String anketID,String anket_question, String anket_image1, String anket_image2,String anket_image3,int oy1, int oy2, int oy3, String user_image,String user_name,String user_username,String btnDrm,String anketKulId) {
        this.anket_question = anket_question;
        this.anket_image1 = anket_image1;
        this.anket_image2 = anket_image2;
        this.anket_image3=anket_image3;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_image = user_image;
        this.anketID=anketID;
        this.anketKulId=anketKulId;
        this.oy1=oy1;
        this.oy2=oy2;
        this.oy3=oy3;
        this.btnDrm=btnDrm;
    }

    public String getAnketKulId() {
        return anketKulId;
    }

    public int getOy3() {
        return oy3;
    }

    public String getBtnDrm() {
        return btnDrm;
    }

    public String getAnket_image3() {
        return anket_image3;
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
    public String getAnketID() {
        return anketID;
    }

    public int getOy1() {
        return oy1;
    }

    public int getOy2() {
        return oy2;
    }
}
