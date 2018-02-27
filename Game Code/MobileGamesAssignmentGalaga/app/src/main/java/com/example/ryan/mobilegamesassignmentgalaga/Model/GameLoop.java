package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 27/02/2018.
 */

public class GameLoop extends SurfaceView implements Runnable{

    // Constructor

    public GameLoop(Context context, Point screens)
    {
        super(context);

        holder = getHolder();

        screenSize = screens;

    }

    // Data Members

    private boolean ok = false;

    Thread t = null;

    SurfaceHolder holder;

    Paint paint;

    Point screenSize;

    // Member Functions

    // This wil be used to create a separate thread. This thread will be used for the main game loop.
    public void run()
    {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (ok)
        {
            if (!holder.getSurface().isValid())
            {
                continue;
            }

            Canvas gameCanvas = holder.lockCanvas();

            this.updateCanvas();

            this.drawCanvas(gameCanvas);

            holder.unlockCanvasAndPost(gameCanvas);
        }

        Log.e(TAG, "run: Game Running.");
    }

    // This will be used to pause the game.
    public void pause()
    {
        ok = false;

        while (true)
        {
            try
            {
                t.join();

            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            break;
        }

        t = null;

        Log.d(TAG, "pause: Game paused.");
    }

    // This will be used to resume the game.
    public void resume()
    {
        ok = true;

        t = new Thread(this);

        t.start();

        Log.d(TAG, "resume: Game Resumed");
    }

    // This is where everything in the game will be drawn.
    protected void updateCanvas()
    {

    }

    // This will handle the drawing of the canvas.
    protected void drawCanvas(Canvas gameCanvas)
    {
        gameCanvas.drawARGB(255, 0, 0, 0);

    }
}
