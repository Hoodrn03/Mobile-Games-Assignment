package com.example.ryan.mobilegamesassignmentgalaga.View;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.ryan.mobilegamesassignmentgalaga.Model.GameLoop;

public class MainGame extends AppCompatActivity {

    GameLoop gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Flags to set the to a full screen without any task bar.
        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        //Set the flags on the view
        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);

        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;

        Point screenSize = new Point(widthPixels, heightPixels);

        gl = new GameLoop(this, screenSize);

        setContentView(gl);


    }


    @Override
    protected void onPause()
    {
        super.onPause();

        gl.pause();

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        gl.resume();

    }


}
