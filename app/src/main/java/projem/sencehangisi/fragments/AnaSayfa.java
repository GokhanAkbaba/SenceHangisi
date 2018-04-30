package projem.sencehangisi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projem.sencehangisi.Controls.AnketInfo;
import projem.sencehangisi.Controls.Anket_adapter;
import projem.sencehangisi.Controls.WebServisLinkleri;
import projem.sencehangisi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnaSayfa extends Fragment {
    boolean isImageFitToScreen=false;

    private RecyclerView mRecyclerView;
    private Anket_adapter mAnket_adapter;
    private ArrayList<AnketInfo> mInfoArrayList;
    private RequestQueue mRequestQueue;
    ImageButton imgBtn;
    boolean deger=false;
    public AnaSayfa()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.fragment_ana_sayfa, container, false);
             mRecyclerView=view.findViewById(R.id.recycler_view);
               //mRecyclerView.setHasFixedSize(true);
               mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mInfoArrayList=new ArrayList<>();
                mRequestQueue= Volley.newRequestQueue(getActivity());

        parseJson();
                return view;
    }


    private void parseJson(){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, WebServisLinkleri.AnketCEK, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("Anketler");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject anket=jsonArray.getJSONObject(i);
                                String user_ad=anket.getString("ad_soyad");
                                String user_kulAdi=anket.getString("kul_adi");
                                String user_img=anket.getString("kul_image");
                                String anket_soru=anket.getString("soru");
                                String anket_img1=anket.getString("resim1");
                                String anket_img2=anket.getString("resim2");
                                mInfoArrayList.add(new AnketInfo(anket_soru,anket_img1,anket_img2,user_ad,user_kulAdi,user_img));
                            }
                            mAnket_adapter=new Anket_adapter(getActivity(),mInfoArrayList);
                            mRecyclerView.setAdapter(mAnket_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
           error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }









}
