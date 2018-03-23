package projem.sencehangisi.Controls;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
/**
 * Created by cannet on 19.03.2018.
 */
public class WebServis extends AsyncTask<String,Void,String>
{
    @Override
    protected String doInBackground(String... strings) {
        String WebServisAdres=strings[0];
        String json="";
        try{
            URL url=new URL(WebServisAdres);
            json=getStringFromInputStream(url.openStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return json;
    }
    private String getStringFromInputStream(InputStream is)
    {
        BufferedReader br=null;
        StringBuilder sb=new StringBuilder();

        String line;

        try
        {
            br=new BufferedReader(new InputStreamReader(is));
            while((line=br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(br != null)
            {
                try{
                    br.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
