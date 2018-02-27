package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ryan on 24/02/2018.
 */

// This will hold all of the items relating to the player. and the player's functionality.

public class Player {

    // Constructor

    Player()
    {

    }

    // Data Members

    // Member Functions


    public void drawPlayer(Paint p, Canvas c)
    {
        p.setColor(Color.BLUE);

        c.drawRect(10, 10, 10, 10, p);
    }

}
