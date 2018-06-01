package projem.sencehangisi.Controls;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import projem.sencehangisi.R;
import projem.sencehangisi.fragments.KullaniciProfiliActivity;

/**
 * Created by cannet on 30.05.2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("mesajCesidi"),remoteMessage.getData().get("message"),remoteMessage.getData().get("kul_id")
                ,remoteMessage.getData().get("ad_soyad"),remoteMessage.getData().get("kul_adi"),remoteMessage.getData().get("kul_image")
                ,remoteMessage.getData().get("kapak_foto"));
    }

    private void showNotification(String mesajCesidi,String message,String kul_id,String adi,String kullaniciAdi,String profilResBilgi,String profilKpkBilgi) {

            Intent i = new Intent(this, KullaniciProfiliActivity.class);
            i.putExtra("kul_id",kul_id);
            i.putExtra("Adi",adi);
            i.putExtra("KullaniciAdi",kullaniciAdi);
            i.putExtra("resim",profilResBilgi);
            i.putExtra("kapak_foto",profilKpkBilgi);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri ses= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Sence Hangisi")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message))
                    .setTicker(mesajCesidi)
                    .setSound(ses)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.question)
                    .setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());
        }


}
