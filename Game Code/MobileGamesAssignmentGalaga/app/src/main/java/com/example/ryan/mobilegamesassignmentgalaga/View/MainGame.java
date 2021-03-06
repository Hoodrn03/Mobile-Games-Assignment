package com.example.ryan.mobilegamesassignmentgalaga.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.ryan.mobilegamesassignmentgalaga.EndGame;
import com.example.ryan.mobilegamesassignmentgalaga.Model.GameLoop;
import com.example.ryan.mobilegamesassignmentgalaga.R;

import static android.content.ContentValues.TAG;

public class MainGame extends AppCompatActivity {

    //------------------------------------------------------------------------
    // Variables :
    //------------------------------------------------------------------------

    // This will hold the entire game.
    GameLoop gl;

    MediaPlayer mediaPlayer;

    //------------------------------------------------------------------------
    // Functions :
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // This will be called when the activity is first created.
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

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mediaPlayer = MediaPlayer.create(this, R.raw.tranquility);

        mediaPlayer.start();

        gl = new GameLoop(this, screenSize);

        setContentView(gl);

    }

    //------------------------------------------------------------------------
    // This will be called when the game is paused (exited while still open).
    @Override
    public void onPause()
    {
        super.onPause();

        mediaPlayer.pause();

        gl.pause();

        setContentView(R.layout.pause_activity);

    }

    public void resume(View view)
    {
        mediaPlayer.start();

        setContentView(gl);

        onResume();
    }

    //------------------------------------------------------------------------
    // This will be called when the activity is reopened (from a paused state).
    @Override
    public void onResume()
    {
        super.onResume();

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

        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gl.resume();

    }


}
