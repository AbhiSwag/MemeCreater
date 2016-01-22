package com.example.abhisek.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Abhisek on 8/27/2015.h
 */


public class TopActivity extends Fragment {
    private static EditText topText;
    private static EditText bottomText;

    TopListener mainRef;

    public interface TopListener {
        void makeMeme(String t, String b);
        void selectPicture();
        void savePicture();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mainRef = (TopListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top, container, false);

        TextView title = (TextView)view.findViewById(R.id.title);
        Typeface blockFonts = Typeface.createFromAsset(getActivity().getAssets(), "Pacifico.ttf");
        title.setTypeface(blockFonts);

        topText = (EditText)view.findViewById(R.id.topText);
        bottomText = (EditText)view.findViewById(R.id.bottomText);

        final Button button = (Button)view.findViewById(R.id.button);
        final Button photoSearch = (Button)view.findViewById(R.id.photoSearch);
        final Button saveMeme = (Button)view.findViewById(R.id.saveMeme);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainRef.makeMeme(topText.getText().toString(), bottomText.getText().toString());
            }
        });

        photoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainRef.selectPicture();
            }
        });

        saveMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainRef.savePicture();

            }
        });

        return view;
    }


}
