package projem.sencehangisi.Controls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by cannet on 16.05.2018.
 */

public class Takipciİslemleri {
    private Context mContext;

    public void TakipciEkle(final String userID,final String takipciID) {
        String tag_string_req = "takipci_ekle";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.TakipciEkle, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Anket Oyla: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(mContext, "Tebrikler Takip ediyorsunuz!", Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("takip_eden_kullanici", userID);
                params.put("takip_edilen_kullanici", takipciID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void TakipciBirak(final String userID,final String takipciID) {
        String tag_string_req = "takipci_birak";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServisLinkleri.TakipciBirak, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(mContext, "Tebrikler Takip Etmeyi Bıraktınız!", Toast.LENGTH_LONG).show();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("takip_eden_kullanici", userID);
                params.put("takip_edilen_kullanici", takipciID);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void toast(String x){
        Toast.makeText(mContext, x, Toast.LENGTH_SHORT).show();
    }

}
