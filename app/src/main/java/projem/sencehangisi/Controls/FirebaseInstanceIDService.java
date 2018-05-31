package projem.sencehangisi.Controls;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by cannet on 30.05.2018.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private UserInfo userInfo;
    String kul_ID;
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        userInfo=new UserInfo(this);
            kul_ID=userInfo.getKeyId();
        registerToken(token,kul_ID);
        Log.d(TAG, "Token: " + token);
        Log.d(TAG, "Kul_id: " + kul_ID);
    }

    private void registerToken(String token,String kul_id){
        okhttp3.OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .add("kul_id",kul_id)
                .build();

        Request request = new Request.Builder()
                .url("http://oftekfakitiraf.com/senceHangisi/bildirimRegister.php")
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
