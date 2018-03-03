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

import java.util.ArrayList;
import java.util.Vector;

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

        player = new Player(screenSize.y - (screenSize.y / 4));

        leftButton = new InputButton();
        rightButton = new InputButton();
        fireButton = new InputButton();

        a_ListOfProjectiles = new ArrayList<Projectile>();

        enemy = new Enemies();

        // Set Bitmap Sizes.

        background.setImageSize(screenSize.x, screenSize.y);

        // Set Button Sizes.

        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();

        paint.setColor(Color.GREEN);
        paint2.setColor(Color.CYAN);
        paint3.setColor(Color.RED);

        leftButton.setZoneBounds(0, screenSize.y - (screenSize.y / 8), screenSize.x / 2, screenSize.y, paint);
        rightButton.setZoneBounds(screenSize.x / 2, screenSize.y - (screenSize.y / 8), screenSize.x, screenSize.y, paint2);
        fireButton.setZoneBounds(0, screenSize.y - (screenSize.y / 4), screens.x, screenSize.y - (screenSize.y / 8), paint3);

        // Initialise Player.

        player.setBounds(0, screenSize.x);

        // Set Bitmaps.

        player.setPlayer(this.getContext());

        background.setBackground(this.getContext());

        enemy.setEnemy(this.getContext());

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

    private boolean bHasShot = false;

    //------------------------------------------------------------------------
    // Classes :
    //------------------------------------------------------------------------

    // This will hold the background for the game.
    private Background background;

    // This will hold all of the function and actions for the player, as well as their sprite.
    private Player player;

    // These will be used to detect input from the player.
    private InputButton leftButton;

    private InputButton rightButton;

    private InputButton fireButton;

    // This will hold all of the projectiles the player will shoot.
    private ArrayList<Projectile> a_ListOfProjectiles;

    // This will hold all of the enemies and control all of their functions.
    private Enemies enemy;

    //------------------------------------------------------------------------
    // Member Functions :
    //------------------------------------------------------------------------

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

        // Log.e(TAG, "(run) Game Running.");
    }

    //------------------------------------------------------------------------
    // This will be used to check for player input.
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventAction = event.getAction();

        switch(eventAction)
        {
            // Pressed Screen.
            case MotionEvent.ACTION_DOWN:

                // Log.d(TAG, "(On Touch Event) Pressed");

                // Log.e(TAG, fingerPosX + " " + fingerPosY);

                break;

            // Moved On Screen.
            case MotionEvent.ACTION_MOVE:

                // Log.d(TAG, "(On Touch Event) Moving");

                fingerPosX = event.getX();
                fingerPosY = event.getY();

                break;

            // Leaves The Screen.
            case MotionEvent.ACTION_UP:

                // Log.d(TAG, "(On Touch Event) Released");

                fingerPosX = 0;
                fingerPosY = 0;

                break;
        }

        return true;
    }

    //------------------------------------------------------------------------
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

        // Log.e(TAG, "(Pause) Game paused.");
    }

    //------------------------------------------------------------------------
    // This will be used to resume the game.
    public void resume()
    {
        ok = true;

        t = new Thread(this);

        t.start();

        // Log.e(TAG, "(Resume) Game Resumed");
    }

    //------------------------------------------------------------------------
    // This will be used to update all of the game objects in the game.
    protected void update()
    {
        // Update Game Objects.

        // Player :

        player.updatePlayer();

        // Buttons :

        this.checkButtons();

        // Projectiles :

        this.updateProjectiles();

        // Enemies :

        updateEnemies();

    }

    //------------------------------------------------------------------------
    // This will be used to check all of the buttons for the player's input.
    protected void checkButtons()
    {
        if(leftButton.checkZone(((int) fingerPosX), (int) (fingerPosY)))
        {
            player.movePlayer(true);
        }

        if (rightButton.checkZone(((int) fingerPosX), (int) (fingerPosY)))
        {
            player.movePlayer(false);
        }

        if(fireButton.checkZone(((int) fingerPosX), (int) (fingerPosY)) && !bHasShot)
        {
            Projectile projectile = new Projectile(player.getPlayerX(), player.getPlayerY(), -10);

            projectile.setProjectile(this.getContext());

            // Log.e(TAG, "(Update) Fire");

            a_ListOfProjectiles.add(projectile);

            a_ListOfProjectiles.trimToSize();

            bHasShot = true;

        }
    }

    //------------------------------------------------------------------------
    // This will be used to hold all of the projectiles main functionality.
    protected void updateProjectiles()
    {
        for (int i = 0; i < a_ListOfProjectiles.size(); i++)
        {
            if(a_ListOfProjectiles.get(i) != null)
            {
                try {
                    a_ListOfProjectiles.get(i).updateProjectile();

                    if (a_ListOfProjectiles.get(i).getMarkedForDeletion())
                    {
                        a_ListOfProjectiles.remove(i);

                        a_ListOfProjectiles.trimToSize();

                        Log.e(TAG, "(Update) Projectile Hit and Removed : " + a_ListOfProjectiles.size());

                        bHasShot = false;
                    }
                }
                catch (Exception e)
                {
                    // Log.e(TAG, "(Update) No Projectiles To Update. ");

                    break;
                }
            }
        }
    }

    protected void updateEnemies()
    {

        if(enemy != null)
        {
            for (int i = 0; i < a_ListOfProjectiles.size(); i++)
            {
                if (a_ListOfProjectiles.get(i) != null)
                {
                    if (enemy.enemyHit(a_ListOfProjectiles.get(i).getProjectileX(), a_ListOfProjectiles.get(i).getProjectileY()))
                    {
                        a_ListOfProjectiles.get(i).setHasCollided(true);
                    }
                }
            }

            enemy.updateEnemy();

            if (enemy.getMarkForDeletion())
            {
                enemy = null;

                Log.e(TAG, "(Update) Enemy Hit and Removed : ");
            }
        }
    }

    //------------------------------------------------------------------------
    // This will handle the drawing of the canvas.
    protected void drawCanvas(Canvas gameCanvas)
    {
        // Draw Items to the canvas.

        // Backgrounds
        background.drawBackground(gameCanvas);

        // Players
        player.drawPlayer(gameCanvas);

        // Buttons

        leftButton.drawZone(gameCanvas);
        rightButton.drawZone(gameCanvas);
        fireButton.drawZone(gameCanvas);

        // Enemies

        if(enemy != null)
        {
            enemy.drawEnemy(gameCanvas);
        }

        // Projectiles

        for (int i = 0; i < a_ListOfProjectiles.size(); i++)
        {
            if(a_ListOfProjectiles.get(i) != null)
            {
                a_ListOfProjectiles.get(i).drawProjectile(gameCanvas);
            }
            else
            {
                // Log.e(TAG, "(Draw Canvas) Array Out Of Bounds :- Projectiles.");

                break;
            }
        }

    }
}
