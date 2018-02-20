package com.example.ryan.mobilegamesassignmentgalaga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
    }

    // This will take the player back to the main menu when pressed.

    public void backToMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
