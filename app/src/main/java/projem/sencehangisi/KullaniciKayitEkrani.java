package projem.sencehangisi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KullaniciKayitEkrani extends Fragment {
    private static final String TAG = KullaniciKayitEkrani.class.getSimpleName();
    @BindView(R.id.kayitEpostaText) EditText kayitEpostaTxt;
    @BindView(R.id.kullaniciKayitFoto) ImageView kullaniciKayitFoto;
    @BindView(R.id.kayitAdSoyadText) EditText kayitAdSoyadTxt;
    @BindView(R.id.kayitKullaniciAdiText) EditText kayitKullaniciAdiTxt;
    @BindView(R.id.kayitSifreText) EditText kayitSifreTxt;
    @BindView(R.id.kayitSifreTekrarText) EditText kayitSifreTekrariTxt;
    @BindView(R.id.kayitButton) Button kayitBtn;
    private ProgressDialog PD;
      public KullaniciKayitEkrani() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_kullanici_kayit_ekrani, container, false);
        ButterKnife.bind(this,view);
        PD=new ProgressDialog(getActivity());
        PD.setCancelable(false);
        kayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kayitkullaniciadsoyad=kayitAdSoyadTxt.getText().toString().trim();
                String kayitkullaniciadi=kayitKullaniciAdiTxt.getText().toString().trim();
                String kayitsifre=kayitSifreTxt.getText().toString();
                String kayitsifretekrari=kayitSifreTekrariTxt.getText().toString();
                String kayiteposta=kayitEpostaTxt.getText().toString();
                if(!kayitkullaniciadsoyad.isEmpty() && !kayitkullaniciadi.isEmpty() && !kayiteposta.isEmpty() && !kayitsifre.isEmpty() && !kayitsifretekrari.isEmpty())
                {
                    if(kayiteposta.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(kayiteposta).matches())
                    {
                        kayitEpostaTxt.setError("Geçerli bir e-posta adresi giriniz");
                    }
                    else
                    {
                        if(kayitsifre.equals(kayitsifretekrari))
                        {
                            kullaniciKayidi(kayitkullaniciadi,kayiteposta,kayitkullaniciadsoyad,kayitsifre,kayitsifretekrari);
                        }
                        else
                        {
                            kayitSifreTekrariTxt.setError("Lütfen Şifrenizi kontrol edin");
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Lütfen Bigileri Tamamlayınız!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    private void kullaniciKayidi(final String kul_adi, final String email,final String ad_soyad,final String sifre, final String sifre_tekrar)
    {
        String tag_string_req="req_register";
        PD.setMessage("Kayıt Olunuyor.");
        showDialog();
        StringRequest istek =new StringRequest(Request.Method.POST, WebServisLinkleri.KAYIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Kayıt Sonucu" + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean hata = jObj.getBoolean("hata");
                    if (!hata) {
                        KullaniciGirisEkrani kullaniciGirisEkrani=new KullaniciGirisEkrani();
                        android.support.v4.app.FragmentManager fragmentManager=getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.kullaniciKayitEkraniFragment, kullaniciGirisEkrani);
                        fragmentTransaction.commit();
                        Toast.makeText(getActivity(), "Kullanıcı Başarı ile Kayıt Oldu", Toast.LENGTH_LONG).show();
                       getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        String hataMesaji = jObj.getString("hata_msg");
                        Toast.makeText(getActivity(), hataMesaji, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG," Kayıt Olurken Hata Oluştu "+error.getMessage());
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){ @Override
            protected Map<String, String> getParams(){
            Map<String,String> params=new HashMap<String,String>();
            params.put("kul_adi",kul_adi);
            params.put("email",email);
            params.put("ad_soyad",ad_soyad);
            params.put("sifre",sifre);
            params.put("sifre_tekrar",sifre_tekrar);
            return params;
        }
        };
        AppController.getInstance().addToRequestQueue(istek,tag_string_req);
    }
    private void showDialog()
    {
        if(!PD.isShowing())
        {
            PD.show();
        }
    }
    private void hideDialog()
    {
        if(PD.isShowing())
        {
            PD.dismiss();
        }
    }
}
