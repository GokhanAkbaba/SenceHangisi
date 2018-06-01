package projem.sencehangisi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import projem.sencehangisi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SifremiUnuttum extends Fragment{

    Intent intent=new Intent();
    public SifremiUnuttum() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sifremi_unuttum, container, false);
        Button sifremiSifirla=(Button) view.findViewById(R.id.SifremiSifirla);
        sifremiSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("05380793813", null, "yeniSifreniz inam61.", null, null);

            }
        });
        return view;
    }

}
