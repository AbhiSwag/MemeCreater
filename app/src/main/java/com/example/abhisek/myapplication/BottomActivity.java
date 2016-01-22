package com.example.abhisek.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

/*
 * Created by Abhisek on 8/27/2015.
 */


public class BottomActivity extends Fragment{
    private static TextView topTextOut;
    private static TextView bottomTextOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom, container, false);

        topTextOut = (TextView)view.findViewById(R.id.topTextOut);
        bottomTextOut = (TextView)view.findViewById(R.id.bottomTextOut);

        return view;
    }

    public void setMemeText(String top, String bottom) {
        Typeface blockFonts = Typeface.createFromAsset(getActivity().getAssets(), "impact.ttf");

        topTextOut.setText(top);
        topTextOut.setAllCaps(true);
        topTextOut.setTypeface(blockFonts);
        topTextOut.setShadowLayer(1, 8, 8, Color.BLACK);

        bottomTextOut.setText(bottom);
        bottomTextOut.setAllCaps(true);
        bottomTextOut.setTypeface(blockFonts);
        bottomTextOut.setShadowLayer(1, 8, 8, Color.BLACK);
    }

}
