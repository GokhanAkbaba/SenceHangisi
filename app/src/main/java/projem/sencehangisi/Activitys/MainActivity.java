package projem.sencehangisi.Activitys;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import projem.sencehangisi.Controls.AppController;
import projem.sencehangisi.Controls.OturumYonetimi;
import projem.sencehangisi.Controls.UserInfo;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciGirisEkrani;
import projem.sencehangisi.fragments.KullaniciProfiliActivity;


public class  MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView mImageView;
    TextView takipEden,takipEdilen;
    Toolbar toolbar;
    private OturumYonetimi userSession;
    private TabLayout tabLayout;
    private UserInfo userInfo;
    KullaniciProfiliActivity kullaniciProfiliActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.anketOlusturBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), AnketOlustur.class);
                startActivity(intent);

            }
        });

        tabLayout= (TabLayout) findViewById(R.id.tab);
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView getUsernameTxt=(TextView) headerView.findViewById(R.id.nav_header_kulAdiTxt);
        TextView getNameTxt=(TextView) headerView.findViewById(R.id.nav_header_adSoyadTxt);
        takipEden=(TextView) headerView.findViewById(R.id.nav_header_takipciTxt);
        takipEdilen=(TextView) headerView.findViewById(R.id.nav_header_takipEdilenTxt);
        mImageView=(ImageView) headerView.findViewById(R.id.nav_header_getImage) ;

        userInfo = new UserInfo(this);
        userSession=new  OturumYonetimi(this);
        if(!userSession.girisYapildi()){
            startActivity(new Intent(this, KullaniciGirisEkrani.class));
            finish();
        }
        String username = userInfo.getKeyUsername();
        String name = userInfo.getKeyNAME();
        String image=userInfo.getKeyRESIM();
        getNameTxt.setText(name);
       getUsernameTxt.setText(username);
       getImage(image);
       TakipEden(userInfo.getKeyId());
       TakipEdilen(userInfo.getKeyId());

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

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent=new Intent();
        int id = item.getItemId();
        if (id == R.id.nav_arkadasBul) {

            intent.setClass(getApplicationContext(),ArkadasiniBulActivity.class);
            startActivity(intent);
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

    public void TakipciGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipcilerActivity.class);
        startActivity(intent);
    }
    public void TakipEdileniGoster(View view)
    {
        Intent intent=new Intent(getApplicationContext(), TakipEdilenActivity.class);
        startActivity(intent);
    }
    public void TakipEden(final String userID){
        String tag_string_req = "takipSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipciSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("kullanici");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                               takipEden.setText(sayi);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
    public void TakipEdilen(final String userID){
        String tag_string_req = "takipEdilenSayisi";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebServisLinkleri.TakipEdenSayisi_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray array=jObj.getJSONArray("kullanici");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject oylar=array.getJSONObject(i);
                                String sayi=oylar.getString("sayi");
                                takipEdilen.setText(sayi);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("kullaniciID", userID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }
}
