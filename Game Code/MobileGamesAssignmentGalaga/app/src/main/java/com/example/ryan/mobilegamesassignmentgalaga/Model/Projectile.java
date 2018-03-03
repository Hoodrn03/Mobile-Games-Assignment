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

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 01/03/2018.
 */

public class Projectile {

    //------------------------------------------------------------------------
    // Constructor :
    //------------------------------------------------------------------------

    public Projectile(int newXPos, int newYPos, int direction)
    {

        iProjectileXPos = newXPos;

        iProjectileYPos = newYPos;

        iProjectileSpeed = direction;

    }

    //------------------------------------------------------------------------
    // Data Members
    //------------------------------------------------------------------------

    private int iProjectileXPos, iProjectileYPos;

    private int iProjectileSpeed = 5;

    private Bitmap projectile;

    private Bitmap projectileToDraw;

    private Bitmap explosion;

    private Rect projSourceRect;

    private Rect expSourceRect;

    private Rect destRect;

    private int iProjectileHeight, iProjectileWidth;

    private int iExplosionWidth, iExplosionHeight;

    private int iRow = 2;
    private int iColumn = 2;

    private int iColumnCounter = 0;
    private int iRowCounter = 0;

    private boolean bHasCollided = false;

    private boolean bMarkForDeletion = false;


    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    public void updateProjectile() throws InterruptedException {

        if(!bHasCollided)
        {
            iProjectileYPos += iProjectileSpeed;

            // Log.e(TAG, "(Update Projectile) Projectile Pos : " + iProjectileXPos + " " + iProjectileYPos);

            destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);

            if (iProjectileYPos <= 0)
            {
                bHasCollided = true;
            }
        }

        else
        {
            frameToDraw();

            expSourceRect.left = iColumnCounter * iExplosionWidth;
            expSourceRect.right = expSourceRect.left + iExplosionWidth;
            expSourceRect.top = iRowCounter * iExplosionHeight;
            expSourceRect.bottom = expSourceRect.top + iExplosionHeight;

            bMarkForDeletion = true;

        }

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


    public boolean getMarkedForDeletion()
    {
        return bMarkForDeletion;
    }

    public void setProjectile(Context context)
    {
        // Set Bitmaps.


        // Projectile :
        Resources res = context.getResources();

        projectile = BitmapFactory.decodeResource(res, R.drawable.missile);

        projectileToDraw = Bitmap.createScaledBitmap(projectile, projectile.getWidth() / 4, projectile.getHeight() / 8, false);

        // Explosion :
        explosion = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Prepare Separate Frames.

        iProjectileHeight = projectileToDraw.getHeight();

        iProjectileWidth = projectileToDraw.getWidth();

        iExplosionHeight = explosion.getHeight();

        iExplosionWidth = explosion.getWidth();

        projSourceRect = new Rect(0, 0, iProjectileWidth, iProjectileHeight);

        expSourceRect = new Rect(0, 0, iExplosionWidth, iExplosionHeight);

        destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);
    }

    public void setHasCollided(boolean hasHit)
    {
        bHasCollided = hasHit;
    }

    public int getProjectileX()
    {
        return iProjectileXPos;
    }

    public int getProjectileY()
    {
        return iProjectileYPos;
    }

    public void drawProjectile(Canvas canvas)
    {
        if(!bHasCollided)
        {
            if (projectileToDraw != null)
            {
                canvas.drawBitmap(projectileToDraw, projSourceRect, destRect, null);
            }
            else
            {
                Log.e(TAG, "(drawProjectile) Unable to draw item.");
            }
        }

        else
        {
            if (explosion != null)
            {
                canvas.drawBitmap(explosion, expSourceRect, destRect, null);
            }
            else
            {
                Log.e(TAG, "(drawProjectile) Unable to draw item.");
            }
        }

    }

}
