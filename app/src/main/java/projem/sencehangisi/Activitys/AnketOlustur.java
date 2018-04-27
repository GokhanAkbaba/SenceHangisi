package projem.sencehangisi.Activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import projem.sencehangisi.Controls.BackgroundTask;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.R;

public class AnketOlustur extends AppCompatActivity  {

    private static final String TAG = AnketOlustur.class.getSimpleName();
    @BindView(R.id.anketSorusuTxt) MultiAutoCompleteTextView anketSorusuTxt;
    @BindView(R.id.anketSecenekFoto1) ImageView anketSecenekFoto1;
    @BindView(R.id.anketSecenekFoto2) ImageView anketSecenekFoto2;
    @BindView(R.id.anketGonderBtn) ImageView anketGonderBtn;
    private Bitmap bitmap,defaults;
    private ProgressDialog PD;
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
        anketGonderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anketSorusu=anketSorusuTxt.getText().toString().trim();
                if(!anketSorusu.isEmpty())
                {
                    anketKayit(this);
                }
                else
                {
                    Toast.makeText(AnketOlustur.this,"Lütfen Anket Sorusunu Giriniz!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void anketKayit(View.OnClickListener view)
    {
        userInfo=new UserInfo(this);
        String soru,anketFoto1,kullanici_id;
        if (bitmap == null) {
            anketFoto1=getStringImage(defaults);
        } else {
            anketFoto1=getStringImage(bitmap);
        }

        soru=anketSorusuTxt.getText().toString();
        kullanici_id=userInfo.getKeyId();
        String method="register";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute(method,soru,anketFoto1,kullanici_id);
        finish();
    }
    public void anketResimSec(View v)
    {
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
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    anketSecenekFoto1.setImageBitmap(bitmap);

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
