package projem.sencehangisi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SifremiUnuttum extends Fragment{
    public SifremiUnuttum() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sifremi_unuttum, container, false);
        final EditText sifremiUnuttum=(EditText) view.findViewById(R.id.SifremiUnuttumTxt);
        final Button sifremiSifirla=(Button) view.findViewById(R.id.SifremiSifirla);

        sifremiSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sifremiUnuttum.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(sifremiUnuttum.getText().toString()).matches())
                {
                    sifremiUnuttum.setError("Geçerli bir e-posta adresi giriniz");
                }
                else {
                    SifreSifirla(sifremiUnuttum.getText().toString());
                }
            }
        });
        return view;
    }

    private void SifreSifirla(final String eposta) {
        String tag_string_req = "req_signup";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.SifremiSifirla, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Şifre Değişimi: " + response.toString());
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (error) {
                        KullaniciGirisEkrani kullaniciGirisEkrani = new KullaniciGirisEkrani();
                        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.SifremiUnuttumLayout, kullaniciGirisEkrani);
                        fragmentTransaction.commit();
                        Toast.makeText(getActivity(), "Şifreniz e-posta adresinize başarıyla gönderilmiştir.", Toast.LENGTH_LONG).show();
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    toast("Json error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Şifre Değişimi: " + error.getMessage());
                toast("Şifre Değiştirme İşlemi Sırasında Sorun Oluştu.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("postaAdresi",eposta);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
    private void toast(String x){
        Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
    }

}
