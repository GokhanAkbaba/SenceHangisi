package projem.sencehangisi.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
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
import butterknife.Unbinder;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

public class AnketOlustur extends AppCompatActivity  {

    private static final String TAG = AnketOlustur.class.getSimpleName();
    @BindView(R.id.anketSorusuTxt) MultiAutoCompleteTextView anketSorusuTxt;
    @BindView(R.id.anketSecenekFoto1) ImageView anketSecenekFoto1;
    @BindView(R.id.anketSecenekFoto2) ImageView anketSecenekFoto2;
    @BindView(R.id.anketGonderBtn) ImageView anketGonderBtn;
    private Bitmap bitmap,bitmap2,defaults;
    private UserInfo userInfo;
    private static final String IMAGE_DIRECTORY = "/Sence Hangisi";
    private int GALLERY = 1, CAMERA = 2;
    Unbinder unbinder;
    private OturumYonetimi session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anket_olustur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        defaults = BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera);
        ButterKnife.bind(this);
        userInfo = new UserInfo(this);
        session=new OturumYonetimi(AnketOlustur.this);


        anketGonderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anketSorusu=anketSorusuTxt.getText().toString().trim();
                String user=userInfo.getKeyId();
                if(!anketSorusu.isEmpty())
                {
                    anketKayit(user,anketSorusu);
                    finish();
                }
                else
                {
                    Toast.makeText(AnketOlustur.this,"Lütfen Anket Sorusunu Giriniz!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void anketKayit(final String userID,final String anketSoru)
    {
        String tag_string_req = "Anket_req";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.AnketOlustur, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent intent = new Intent(
                                AnketOlustur.this,
                                MainActivity.class);
                        startActivity(intent);
                        toast("Anket Oluşturuldu");
                        finish();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
                Log.e(TAG, "Anket Error: " + error.getMessage());
                toast("Unknown Error occurred");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("kullanici_id", userID);
                params.put("soru", anketSoru);

                if (bitmap == null) {
                    params.put("anketFoto1", getStringImage(defaults));
                } else {
                    params.put("anketFoto1", getStringImage(bitmap));
                    params.put("anketFoto2", getStringImage2(bitmap2));
                }
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage2(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    int deger;
    public void anketResimSec1(View v)
    {
        deger=1;
        showPictureDialog();
    }
    public void anketResimSec2(View v)
    {
        deger=2;
        showPictureDialog();
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(AnketOlustur.this);
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
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    if(deger==1)
                    {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        anketSecenekFoto1.setImageBitmap(bitmap);
                    }
                    else if(deger==2)
                    {
                        bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        anketSecenekFoto2.setImageBitmap(bitmap2);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Resim Seçilemedi", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            anketSecenekFoto1.setImageBitmap(bitmap);
            saveImage(bitmap);
            Toast.makeText(this, "Fotoğraf Kaydedildi.", Toast.LENGTH_SHORT).show();
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
            MediaScannerConnection.scanFile(this,
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
    private void toast(String x){
        Toast.makeText(AnketOlustur.this, x, Toast.LENGTH_SHORT).show();
    }
    public void anketOlusturKpt(View v)
    {
        finish();
    }

}
