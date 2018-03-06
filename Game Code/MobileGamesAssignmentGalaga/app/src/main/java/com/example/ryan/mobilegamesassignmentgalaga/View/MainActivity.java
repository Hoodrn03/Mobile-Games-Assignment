package com.example.ryan.mobilegamesassignmentgalaga.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ryan.mobilegamesassignmentgalaga.ControlsActivity;
import com.example.ryan.mobilegamesassignmentgalaga.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    // This will start the game.

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainGame.class);

        startActivity(intent);
    }

    public void controls(View view)
    {
        Intent intent = new Intent(this, ControlsActivity.class);

        startActivity(intent);
    }


}
