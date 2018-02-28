package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
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

        leftButton = new InputButton();
        rightButton = new InputButton();

        // Set Bitmap Sizes.

        background.setImageSize(screenSize.x, screenSize.y);

        // Set Button Sizes.

        Paint paint = new Paint();
        Paint paint2 = new Paint();


        paint.setColor(Color.RED);
        paint2.setColor(Color.CYAN);

        leftButton.setZoneBounds(0, screenSize.y - (screenSize.y / 8), screenSize.x / 2, screenSize.y, paint);
        rightButton.setZoneBounds(screenSize.x / 2, screenSize.y - (screenSize.y / 8), screenSize.x, screenSize.y, paint2);

        // Set Bitmaps.

        player.setPlayer(this.getContext());

        background.setBackground(this.getContext());

    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    // This will be used for the main game loop. While this is true the game will loop.
    private boolean ok = false;

    private Thread t = null;

    private SurfaceHolder holder;


    // This will be used to hold the current device's screen size.
    private Point screenSize;

    // This will be used to check if and where the player currently has pressed.
    private float fingerPosX, fingerPosY;

    //------------------------------------------------------------------------
    // Classes :
    //------------------------------------------------------------------------

    private Background background;

    private Player player;

    private InputButton leftButton;

    private InputButton rightButton;

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

            this.update();

            this.drawCanvas(gameCanvas);

            holder.unlockCanvasAndPost(gameCanvas);
        }

        Log.e(TAG, "run: Game Running.");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventAction = event.getAction();

        switch(eventAction)
        {
            // Pressed Screen.
            case MotionEvent.ACTION_DOWN:

                Log.d(TAG, "Pressed...");

                fingerPosX = event.getX();
                fingerPosY = event.getY();

                Log.e(TAG, fingerPosX + " " + fingerPosY);

                break;

            // Moved On Screen.
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Moving!!!");

                break;

            // Leaves The Screen.
            case MotionEvent.ACTION_UP:

                Log.d(TAG, "Released???");

                fingerPosX = 0;
                fingerPosY = 0;

                break;
        }

        return true;
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


    protected void update()
    {
        // Update Game Objects.

        player.updatePlayer();

        if(leftButton.checkZone(((int) fingerPosX), (int) (fingerPosY)))
        {
            player.movePlayer(true);
        }
        if (rightButton.checkZone(((int) fingerPosX), (int) (fingerPosY)))
        {
            player.movePlayer(false);
        }

    }

    // This will handle the drawing of the canvas.
    protected void drawCanvas(Canvas gameCanvas)
    {
        // Draw Items to the canvas.

        background.drawBackground(gameCanvas);

        player.drawPlayer(gameCanvas);

        leftButton.drawZone(gameCanvas);
        rightButton.drawZone(gameCanvas);
    }
}
