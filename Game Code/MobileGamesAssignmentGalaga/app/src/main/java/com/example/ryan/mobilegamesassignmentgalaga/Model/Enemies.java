package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;

import com.example.ryan.mobilegamesassignmentgalaga.R;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 24/02/2018.
 */

// This will be the base enemy class and will be expanded upon to give the game some extra functionality.

public class Enemies extends Pathfinder {

    //------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------

    Enemies(Context context, int leftBounds, int rightBounds, int screenSizeX, int screenSizeY)
    {
        this.setEnemy(context);

        this.setBounds(leftBounds, rightBounds);

        Random r = new Random();

        if(r.nextBoolean())
        {
            iEnemyXPos = screenSizeX;
        }

        else
        {
            iEnemyXPos = 0 - iEnemyWidth;
        }


        iEnemyYPos = r.nextInt(screenSizeY);

        iSpeed = r.nextInt(20) + 5;

        generatePath(screenSizeX, screenSizeY);

    }

    //------------------------------------------------------------------------
    // Data Members
    //------------------------------------------------------------------------

    private int iEnemyXPos, iEnemyYPos;

    private int iEnemyHeight, iEnemyWidth;

    private Rect sourceRect;

    private Rect destRect;

    private Bitmap enemy;

    private Bitmap enemyToDraw;

    private int iRow = 3;
    private int iColumn = 2;

    private int  iRowCounter, iColumnCounter;

    private float fCurrTime, fDeltaTime, fPrevTime, fUpdateTimer;

    private int iHealth;
    private int iDamage = 100;

    private int iSpeed = 10;

    private boolean bMarkForDeletion = false;

    private boolean bMoveLeft = true;

    private int iLeftBounds, iRightBounds;

    private int iShootChance = 500; // 1/ 500.

    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // This will be used to update this enemy during the game.
    public void updateEnemy()
    {
        fCurrTime = SystemClock.elapsedRealtime();

        fDeltaTime = fCurrTime - fPrevTime;

        fUpdateTimer += fDeltaTime;

        // Log.e(TAG, "(Update Player) DELTA TIME : " + fDeltaTime + " Current Timer : " + fUpdateTimer);

        if(fUpdateTimer >= 200)
        {
            this.frameToDraw();

            this.moveEnemy();

            sourceRect.left = iColumnCounter * iEnemyWidth;
            sourceRect.right = sourceRect.left + iEnemyWidth;
            sourceRect.top = iRowCounter * iEnemyHeight;
            sourceRect.bottom = sourceRect.top + iEnemyHeight;

            fUpdateTimer = 0;

            this.fireProjectile();
        }

        if(iHealth <= 0)
        {
            bMarkForDeletion = true;
        }

        // Log.d(TAG, "(Update Player) Left : " + sourceRect.left + " Right : " + sourceRect.right + " Top : " + sourceRect.top + " Bottom : " + sourceRect.bottom);

        fPrevTime = fCurrTime;
    }

    //------------------------------------------------------------------------
    // This will return this enemy's X position.
    public int getEnemyXPos()
    {
        return iEnemyXPos + (iEnemyWidth / 2);
    }

    //------------------------------------------------------------------------
    // This will return this enemy's Y position.
    public int getEnemyYPos()
    {
        return iEnemyYPos + iEnemyHeight;
    }

    //------------------------------------------------------------------------
    // This will be used to keep track of which item on the sprite sheet to draw.
    public void frameToDraw()
    {

        if(iRowCounter >= (iRow - 1))
        {
            iRowCounter = 0;

            iColumnCounter++;
        }

        if(iColumnCounter >= iColumn)
        {
            iColumnCounter = 0;
        }

        iRowCounter++;

        // Log.e(TAG, "Column : " + iColumnCounter + " Row : " + iRowCounter);

    }

    //------------------------------------------------------------------------
    // This will be used to check if this enemy has been hit.
    public boolean enemyHit(int projectileX, int projectileY)
    {
        if(projectileX > iEnemyXPos && projectileX < iEnemyXPos + iEnemyWidth)
        {
            if(projectileY > iEnemyYPos && projectileY < iEnemyYPos + iEnemyHeight)
            {
                Log.e(TAG, "(Enemy Hit) Hull Damage Detected");

                iHealth -= iDamage;

                Log.e(TAG, "(Enemy Hit) Current Health : " + iHealth);

                return true;
            }
        }

        return false;
    }

    //------------------------------------------------------------------------
    // This will be used to allow for the enemy to fire a projectile.
    public boolean fireProjectile()
    {
        Random r = new Random();

        int rand;

        rand = r.nextInt(iShootChance);

        // Log.e(TAG, "(Fire Projectiles) Current Rand : " + rand);

        if(rand == 1)
        {
            // Log.e(TAG, "(Fire Projectiles) : Fire Cannons!");

            return true;
        }

        return false;
    }

    //------------------------------------------------------------------------
    // This will be used to check if this enemy should be deleted.
    public boolean getMarkForDeletion()
    {
        return bMarkForDeletion;
    }

    //------------------------------------------------------------------------
    // This will be used to create the enemy. It will give it a random bitmap and health value.
    public void setEnemy(Context context)
    {
        // Generate Random Number.

        Random r = new Random();

        int rand = r.nextInt(3);

        // Log.e(TAG, "(Set Enemy) Current Random : " + rand);

        // Set the enemy's Bitmap.

        Resources res = context.getResources();

        switch (rand) {
            case 0:

                enemy = BitmapFactory.decodeResource(res, R.drawable.enemy1);

                break;

            case 1:

                enemy = BitmapFactory.decodeResource(res, R.drawable.enemy2);

                break;

            case 2:

                enemy = BitmapFactory.decodeResource(res, R.drawable.enemy3);

                break;
        }

        enemyToDraw = Bitmap.createScaledBitmap(enemy, enemy.getWidth() * 2, enemy.getHeight() * 2, false);

        // Prepare for different frames.

        iEnemyHeight = enemyToDraw.getHeight() / iRow;

        iEnemyWidth = enemyToDraw.getWidth() / iColumn;

        // Set position.

        sourceRect = new Rect(0, 0, iEnemyWidth, iEnemyHeight);

        destRect = new Rect(iEnemyXPos, iEnemyYPos, iEnemyXPos + iEnemyWidth, iEnemyYPos + iEnemyHeight);

        // Set Enemy Health

        iHealth = r.nextInt(200) + 50;


        Log.e(TAG, "(Set Enemy) Enemies Current Health " + iHealth);
    }

    //------------------------------------------------------------------------
    // This will be used to make the enemy move on the screen. (Left To Right).
    public void moveEnemy()
    {

        if(this.endOfPath())
        {
            // Move Enemy (Left and Right).

            if(bMoveLeft)
            {
                if(iEnemyXPos > iLeftBounds)
                {
                    iEnemyXPos -= iSpeed;
                }
            }

            else if(iEnemyXPos + iEnemyWidth < iRightBounds)
            {
                iEnemyXPos += iSpeed;
            }

            // Check Direction.

            if(iEnemyXPos <= iLeftBounds)
            {
                bMoveLeft = false;
            }
            else if(iEnemyXPos + iEnemyWidth >= iRightBounds)
            {
                bMoveLeft = true;
            }

            // Log.e(TAG, "(Move Enemy) : " + iEnemyXPos);

        }

        else
        {
            iEnemyXPos = this.moveAlongPathX(this.iEnemyXPos, this.iSpeed);

            iEnemyYPos = this.moveAlongPathY(this.iEnemyYPos, this.iSpeed);
        }


        destRect = new Rect(iEnemyXPos, iEnemyYPos, iEnemyXPos + iEnemyWidth, iEnemyYPos + iEnemyHeight);
    }

    //------------------------------------------------------------------------
    // This will be used to set the leftmost and rightmost bounds for the player constrining them to
    // the screen.
    public void setBounds(int newLeftBounds, int newRightBounds)
    {
        iLeftBounds = newLeftBounds;

        iRightBounds = newRightBounds;
    }

    //------------------------------------------------------------------------
    // This will be used to draw this enemy.
    public void drawEnemy(Canvas canvas)
    {
        if(enemyToDraw != null)
        {
            canvas.drawBitmap(enemyToDraw, sourceRect, destRect, null);

            // drawPath(canvas);
        }
        else
        {
            Log.e(TAG, "(drawPlayer) Unable to draw item.");
        }

    }


}
