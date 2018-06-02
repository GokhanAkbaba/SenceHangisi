package projem.sencehangisi.Controls.Bildirim;

/**
 * Created by cannet on 02.06.2018.
 */

public class BildirimInfo {
    String kul_id,ad_soyad,kul_image,bilgi,tarih;

    public BildirimInfo() {


    }

    public BildirimInfo(String bldKulID, String bldAdSoyad, String bldKulFoto, String bldBilgi, String bldTarih) {

        this.kul_id=bldKulID;
        this.ad_soyad=bldAdSoyad;
        this.kul_image=bldKulFoto;
        this.bilgi=bldBilgi;
        this.tarih=bldTarih;
    }

    public String getKul_id() {
        return kul_id;
    }

    public String getAd_soyad() {
        return ad_soyad;
    }

    public String getKul_image() {
        return kul_image;
    }

    public String getBilgi() {
        return bilgi;
    }

    public String getTarih() {
        return tarih;
    }
}



