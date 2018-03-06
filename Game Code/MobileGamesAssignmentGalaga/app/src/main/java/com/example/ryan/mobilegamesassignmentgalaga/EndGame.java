package com.example.ryan.mobilegamesassignmentgalaga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ryan.mobilegamesassignmentgalaga.View.MainActivity;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView  tv = (TextView)findViewById(R.id.DisplayScore);

        int message = getIntent().getIntExtra("Score", 0);

        tv.setText("" + message);

    }

    protected void mainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }


}
