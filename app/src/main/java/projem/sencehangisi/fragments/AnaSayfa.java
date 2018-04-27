package projem.sencehangisi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import projem.sencehangisi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnaSayfa extends Fragment {

    ImageView imageView;

    boolean isImageFitToScreen=false;

    public AnaSayfa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ana_sayfa, container, false);

        imageView = (ImageView) view.findViewById(R.id.secenekfoto1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }
}
