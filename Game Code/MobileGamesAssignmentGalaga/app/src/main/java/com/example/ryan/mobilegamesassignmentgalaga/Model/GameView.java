package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

/**
 * Created by Ryan on 24/02/2018.
 */

// This will be used to hold the game's canvas which everything will be drawn onto.

public class GameView {

    // Constructor

    public GameView(Point screenS)
    {
        screenSize = screenS;
    }

    // Data Members

    private Point screenSize;

    Canvas gameCanvas;

    // Member Functions

    // This will be used to lock the game canvas.
    public void lockCanvas(SurfaceHolder holder)
    {
        gameCanvas = holder.lockCanvas();
    }

    // This will unlock the game canvas.
    public void unlockCanvas(SurfaceHolder holder)
    {
        holder.unlockCanvasAndPost(gameCanvas);
    }

    // This will be used to draw the game's canvas.
    public void drawCanvas()
    {
        gameCanvas.drawARGB(255, 100, 100, 0);
    }

    // This will be used to get access to the game's canvas.
    public Canvas getCanvas()
    {
        return gameCanvas;
    }

}
