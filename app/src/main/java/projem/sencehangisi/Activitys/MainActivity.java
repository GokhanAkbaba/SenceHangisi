package projem.sencehangisi.Activitys;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciGirisEkrani;
import projem.sencehangisi.fragments.KullaniciProfiliActivity;


public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_header_kulAdiTxt) TextView getUsernameTxt;
    @BindView(R.id.nav_header_adSoyadTxt) TextView getNameTxt;
    @BindView(R.id.nav_header_getImage) CircleImageView mImageView;
    private OturumYonetimi userSession;
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.anketOlusturBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), AnketOlustur.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.mipmap.navigation_profil_foto);

        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        String username = userInfo.getKeyUsername();
        String name = userInfo.getKeyNAME();
        String image=userInfo.getKeyRESIM();
        //getNameTxt.setText(name);
       // getUsernameTxt.setText(username);
       // getImage(image);
    }
    public  void getImage(final String url){
        String image_req="req_image";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.drawable.arka_plan);
                    }
                });
        AppController.getInstance().addToRequestQueue(request,image_req);
    }
    private void kullaniciCikis()
    {
        userSession.setLogin(false);
        userInfo.clearUserInfo();
        Intent intent=new Intent(MainActivity.this,KullaniciIslemleriActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*@Override
    Sağ Üst Köşedeki 3 nokta
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent=new Intent();
        int id = item.getItemId();
        if (id == R.id.nav_arkadasBul) {
        } else if (id == R.id.nav_ayarlar) {

            intent.setClass(getApplicationContext(),AyarlarActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_bildirim) {

            intent.setClass(getApplicationContext(),BildirimlerActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_profile)
        {
            intent.setClass(getApplicationContext(),KullaniciProfiliActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_populerler)
        {
        }
        else if (id == R.id.nav_cikis)
        {

            kullaniciCikis();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
