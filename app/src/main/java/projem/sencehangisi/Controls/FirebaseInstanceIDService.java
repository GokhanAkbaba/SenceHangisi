package projem.sencehangisi.Controls;

import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by cannet on 30.05.2018.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
       /* UserInfo userInfo=new UserInfo(this);;
        String kul_ID;
        String token = FirebaseInstanceId.getInstance().getToken();
            kul_ID=userInfo.getKeyId();
       // registerToken(token,kul_ID);
        Log.d(TAG, "Token: " + token);
        Log.d(TAG, "Kul_id: " + kul_ID);*/
        /*FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();*/
    }

    /*public void registerToken(String token,String kul_id){
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
    }*/
}
