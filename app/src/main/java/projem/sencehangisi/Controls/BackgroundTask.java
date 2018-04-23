package projem.sencehangisi.Controls;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cannet on 23.04.2018.
 */

public class BackgroundTask extends AsyncTask<String,Void,String>{

    Context ctx;
    public BackgroundTask(Context ctx)
    {
        this.ctx=ctx;
    }



    protected String doInBackground(String... params) {
        String method=params[0];
        if(method.equals("register"))
        {
            String soru=params[1];
            String anketFoto1=params[2];
            String kullanici_id=params[3];
            try {
                URL url=new URL(WebServisLinkleri.AnketOlustur);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("soru","UTF-8")+"="+URLEncoder.encode(soru,"UTF-8")+"&"+
                        URLEncoder.encode("anketFoto1","UTF-8")+"="+URLEncoder.encode(anketFoto1,"UTF-8")+"&"+
                        URLEncoder.encode("kullanici_id","UTF-8")+"="+URLEncoder.encode(kullanici_id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();

                return "Anket Gönderildi.";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}