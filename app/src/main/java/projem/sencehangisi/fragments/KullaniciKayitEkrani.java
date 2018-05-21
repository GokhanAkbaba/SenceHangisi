package projem.sencehangisi.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class KullaniciKayitEkrani extends Fragment {
    private static final String TAG = KullaniciKayitEkrani.class.getSimpleName();
    @BindView(R.id.kayitEpostaText) EditText kayitEpostaTxt;
    @BindView(R.id.kullaniciKayitFoto) CircleImageView kullaniciKayitFoto;
    @BindView(R.id.kayitAdSoyadText) EditText kayitAdSoyadTxt;
    @BindView(R.id.kayitKullaniciAdiText) EditText kayitKullaniciAdiTxt;
    @BindView(R.id.kayitSifreText) EditText kayitSifreTxt;
    @BindView(R.id.kayitSifreTekrarText) EditText kayitSifreTekrariTxt;
    @BindView(R.id.kayitButton) Button kayitBtn;

    private ProgressDialog PD;
    private static final String IMAGE_DIRECTORY = "/Sence Hangisi";
    private int GALLERY = 1, CAMERA = 2;
    private String userChoosenTask;
    Bitmap bitmap;
    Bitmap defaults;
    private UserInfo userInfo;
    private OturumYonetimi session;
    public KullaniciKayitEkrani() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_kullanici_kayit_ekrani, container, false);
          getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        defaults = BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera);

        ButterKnife.bind(this,view);
        PD=new ProgressDialog(getActivity());
        PD.setCancelable(false);
        userInfo = new UserInfo(getActivity());
        session=new OturumYonetimi(getActivity());
        kullaniciKayitFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        kayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kayitkullaniciadsoyad=kayitAdSoyadTxt.getText().toString().trim();
                String kayitkullaniciadi=kayitKullaniciAdiTxt.getText().toString().trim();
                String kayitsifre=kayitSifreTxt.getText().toString();
                String kayitsifretekrari=kayitSifreTekrariTxt.getText().toString();
                String kayiteposta=kayitEpostaTxt.getText().toString();
                String image=kullaniciKayitFoto.toString();
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
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Resim Seç");
        String[] pictureDialogItems = {
                "Galeriden Fotoğraf Seç",
                "Fotoğraf Çek" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    kullaniciKayitFoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Resim Seçilemedi", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
             bitmap = (Bitmap) data.getExtras().get("data");
            kullaniciKayitFoto.setImageBitmap(bitmap);
            saveImage(bitmap);
            Toast.makeText(getActivity(), "Fotoğraf Kaydedildi.", Toast.LENGTH_SHORT).show();
        }
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void kullaniciKayidi(final String kul_adi, final String email,final String ad_soyad,final String sifre,
                                 final String sifre_tekrar) {
        String tag_string_req = "req_signup";
        PD.setMessage("Kayıt olunuyor ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.KAYIT_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
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

                        KullaniciGirisEkrani kullaniciGirisEkrani = new KullaniciGirisEkrani();
                        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.kullaniciKayitEkraniFragment, kullaniciGirisEkrani);
                        fragmentTransaction.commit();
                        Toast.makeText(getActivity(), "Kullanıcı Başarı ile Kayıt Oldu", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                toast("Unknown Error occurred");
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kul_adi", kul_adi);
                params.put("email", email);
                params.put("ad_soyad", ad_soyad);
                params.put("sifre", sifre);
                params.put("sifre_tekrar", sifre_tekrar);
                if (bitmap == null) {
                    params.put("image_path", getStringImage(defaults));
                } else {
                    params.put("image_path", getStringImage(bitmap));
                }
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
        // Adding request to request queue
        private void showDialog() {
            if (!PD.isShowing())
                PD.show();
        }

    private void hideDialog() {
        if (PD.isShowing())
            PD.dismiss();
    }
    private void toast(String x){
        Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
    }

}
