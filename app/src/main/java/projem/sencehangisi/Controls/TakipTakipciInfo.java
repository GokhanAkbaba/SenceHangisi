package projem.sencehangisi.Controls;

/**
 * Created by cannet on 30.04.2018.
 */

public class TakipTakipciInfo {

    private String k_adi,k_adSoyad,k_resmi;
    private String  k_id;
    private int tkpEtImg;
    private String tkpDrm;

    public TakipTakipciInfo(String  k_id,String k_adi,String k_adSoyad,String k_resmi,int tkpEtImg,String tkpDrm)
    {
        this.k_adi=k_adi;
        this.k_id=k_id;
        this.k_adSoyad=k_adSoyad;
        this.k_resmi=k_resmi;
        this.tkpEtImg=tkpEtImg;
        this.tkpDrm=tkpDrm;
    }

    public String getTkpDrm() {
        return tkpDrm;
    }

    public String getK_adi() {
        return k_adi;
    }

    public String getK_adSoyad() {
        return k_adSoyad;
    }

    public String getK_resmi() {
        return k_resmi;
    }

    public String getK_id() {
        return k_id;
    }

    public int getTkpEtImg() {
        return tkpEtImg;
    }
}
