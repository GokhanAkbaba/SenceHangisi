package projem.sencehangisi.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import butterknife.Unbinder;
import projem.sencehangisi.Activitys.MainActivity;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;


public class KullaniciGirisEkrani extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Temel sınıfın basit adını getirir.
    private static final String TAG=KullaniciGirisEkrani.class.getSimpleName();
    private final static String Email="EMAİL MESAJI";
    private ProgressDialog PD;
    private OturumYonetimi session;
    private UserInfo userInfo;@BindView(R.id.epostaGirisText) EditText epostaGirisTxt;


    @BindView(R.id.sifreGirisText) EditText sifreGirisTxt;
    @BindView(R.id.sifreUnuttumText) TextView sifreUnuttumTxt;
    @BindView(R.id.kayitOlText) TextView kayitOlTxt;

    public KullaniciGirisEkrani() {

    }


    public static KullaniciGirisEkrani newInstance(String param1, String param2) {
        KullaniciGirisEkrani fragment = new KullaniciGirisEkrani();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kullanici_giris_ekrani, container, false);
        Button girisYap=(Button)view.findViewById(R.id.GirisYap);
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anaSayfaGec=new Intent(getActivity(),MainActivity.class);
                startActivity(anaSayfaGec);
            }
        });

        unbinder= ButterKnife.bind(this,view );
        PD=new ProgressDialog(getActivity());
        PD.setCancelable(false);
        userInfo= new UserInfo(getActivity());

        session=new OturumYonetimi(getActivity());
        if(session.girisYapildi()){
            startActivity(new Intent(getActivity(), MainActivity.class));
           getActivity().finish();
        }

        girisYap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String eposta=epostaGirisTxt.getText().toString().trim();
                String sifre=sifreGirisTxt.getText().toString();

                if(!eposta.isEmpty() && !sifre.isEmpty())
                {
                    girisYap(eposta,sifre);
                }
                else
                {
                    Toast.makeText(getActivity(),"Lütfen bilgilerinizi girin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void girisYap(final String email, final String sifre){
        // Tag used to cancel the request
        String tag_string_req="req_login";
        PD.setMessage("Giriş..");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.GIRIS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    hideDialog();
                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        JSONObject user = jObj.getJSONObject("user");
                        String uId = user.getString("kul_id");
                        String uName = user.getString("username");
                        String email = user.getString("email");
                        String sifre = user.getString("sifre");
                        String resim = user.getString("resim");
                        String ad_soyad = user.getString("ad_soyad");
                        userInfo.setSifre(sifre);
                        userInfo.setResim(resim);
                        userInfo.setEmail(email);
                        userInfo.setUsername(uName);
                        userInfo.setName(ad_soyad);
                        userInfo.setId(uId);
                        session.setLogin(true);

                        Intent intent =new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
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
                Log.e(TAG, "Giriş Hatası: " + error.getMessage());
                toast("Bilinmeyen hata oluştu");
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("sifre",sifre);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void toast(String x){
        Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
