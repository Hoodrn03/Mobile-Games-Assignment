package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ryan.mobilegamesassignmentgalaga.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 27/02/2018.
 */

public class GameLoop extends SurfaceView implements Runnable{

    //------------------------------------------------------------------------
    // Constructor :
    //------------------------------------------------------------------------

    public GameLoop(Context context, Point screens)
    {
        super(context);

        holder = getHolder();

        screenSize = screens;

        // Initialise Classes.

        background = new Background();

        player = new Player();

        // Set Bitmap Sizes.

        background.setImageSize(screenSize.x, screenSize.y);

        // Set Bitmaps.

        player.setPlayer(this.getContext());

        background.setBackground(this.getContext());

    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    private boolean ok = false;

    private Thread t = null;

    private SurfaceHolder holder;

    private Paint paint;

    private Point screenSize;

    //------------------------------------------------------------------------
    // Classes :
    //------------------------------------------------------------------------

    private Background background;

    private Player player;

    //------------------------------------------------------------------------
    // Member Functions :
    //------------------------------------------------------------------------

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

        Log.e(TAG, "pause: Game paused.");
    }

    // This will be used to resume the game.
    public void resume()
    {
        ok = true;

        t = new Thread(this);

        t.start();

        Log.e(TAG, "resume: Game Resumed");
    }


    protected void updateCanvas()
    {
        // Update Game Objects.

        player.updatePlayer();
    }

    // This will handle the drawing of the canvas.
    protected void drawCanvas(Canvas gameCanvas)
    {
        gameCanvas.drawARGB(255, 0, 0, 0);

        // Draw Items to the canvas.

        background.drawBackground(gameCanvas);

        player.drawPlayer(gameCanvas);


    }
}
