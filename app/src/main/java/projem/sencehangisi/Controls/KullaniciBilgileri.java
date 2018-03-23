package projem.sencehangisi.Controls;

/**
 * Created by cannet on 19.03.2018.
 */

public class KullaniciBilgileri
{
    private int kul_id;
    private int sifre;
    private int sifre_tekrar;
    private String kul_adi;
    private String email;
    private String ad_soyad;
    private String kul_image;

    public KullaniciBilgileri(int kul_id, int sifre, int sifre_tekrar, String kul_adi, String email, String ad_soyad, String kul_image)
    {
        setAd_soyad(ad_soyad);
        setEmail(email);
        setKul_adi(kul_adi);
        setKul_image(kul_image);
        setSifre(sifre);
        setSifre_tekrar(sifre_tekrar);
        setKul_id(kul_id);
    }

    public int getKul_id() {
        return kul_id;
    }

    public void setKul_id(int kul_id) {
        this.kul_id = kul_id;
    }

    public int getSifre() {
        return sifre;
    }

    public void setSifre(int sifre) {
        this.sifre = sifre;
    }

    public int getSifre_tekrar() {
        return sifre_tekrar;
    }

    public void setSifre_tekrar(int sifre_tekrar) {
        this.sifre_tekrar = sifre_tekrar;
    }

    public String getKul_adi() {
        return kul_adi;
    }

    public void setKul_adi(String kul_adi) {
        this.kul_adi = kul_adi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAd_soyad() {
        return ad_soyad;
    }

    public void setAd_soyad(String ad_soyad) {
        this.ad_soyad = ad_soyad;
    }

    public String getKul_image() {
        return kul_image;
    }

    public void setKul_image(String kul_image) {
        this.kul_image = kul_image;
    }
}
