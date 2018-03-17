package projem.sencehangisi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    Bitmap bitmap;
      public KullaniciKayitEkrani() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_kullanici_kayit_ekrani, container, false);
        ButterKnife.bind(this,view);
        PD=new ProgressDialog(getActivity());
        PD.setCancelable(false);
        kullaniciKayitFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Kamera"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Galeri"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Kamera", "Galeri",
                "İptal Et" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Fotoğraf Ekle!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getContext());

                if (items[item].equals("Kamera")) {
                    userChoosenTask ="Kamera";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Galeri")) {
                    userChoosenTask ="Galeri";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("İptal Et")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        kullaniciKayitFoto.setImageBitmap(bitmap);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        bitmap=null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        kullaniciKayitFoto.setImageBitmap(bitmap);

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void kullaniciKayidi(final String kul_adi, final String email,final String ad_soyad,final String sifre,
                                 final String sifre_tekrar)
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
            params.put("image_path",getStringImage(bitmap));
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
