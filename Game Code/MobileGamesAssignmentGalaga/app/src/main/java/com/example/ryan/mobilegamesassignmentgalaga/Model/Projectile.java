package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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

    public Projectile()
    {

    }

    public Projectile(int newXPos, int newYPos, int direction, Context context, int newLowerBounds, int rotation)
    {

        iProjectileXPos = newXPos;

        iProjectileYPos = newYPos;

        iProjectileSpeed = direction;

        this.setProjectile(context, newLowerBounds, rotation);

    }

    //------------------------------------------------------------------------
    // Data Members
    //------------------------------------------------------------------------

    private int iProjectileXPos, iProjectileYPos;

    private int iProjectileSpeed = 5;

    private Bitmap projectile;

    private Bitmap scaledProjectile;

    private Bitmap rotatedProjectile;

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

    private int iLowerBounds;

    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // This will be used to update this projectile within the game.
    public void updateProjectile() throws InterruptedException {

        if(!bHasCollided)
        {
            iProjectileYPos += iProjectileSpeed;

            // Log.e(TAG, "(Update Projectile) Projectile Pos : " + iProjectileXPos + " " + iProjectileYPos);

            destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);

            if (iProjectileYPos <= 0 || iProjectileYPos >= iLowerBounds - 50)
            {
                bHasCollided = true;

                // Log.e(TAG, "(Update Projectiles) : Collided");
            }
        }

        else
        {
            this.frameToDraw();

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

    //------------------------------------------------------------------------
    // This will check if this projectile should be deleted.
    public boolean getMarkedForDeletion()
    {
        return bMarkForDeletion;
    }

    //------------------------------------------------------------------------
    // This will be used to set both of the projectile's bitmaps, the missile and the explosion.
    public void setProjectile(Context context, int newLowerBounds, int rotation)
    {
        // Screen bounds.

        iLowerBounds = newLowerBounds;

        // Set Projectile rotation

        Matrix rotationMatrix = new Matrix();

        rotationMatrix.postRotate(rotation);

        // Set Bitmaps.

        // Projectile :
        Resources res = context.getResources();

        projectile = BitmapFactory.decodeResource(res, R.drawable.missile);

        scaledProjectile = Bitmap.createScaledBitmap(projectile, projectile.getWidth() / 4, projectile.getHeight() / 8, false);

        rotatedProjectile = Bitmap.createBitmap(scaledProjectile, 0, 0, scaledProjectile.getWidth(), scaledProjectile.getHeight(), rotationMatrix, false);

        // Explosion :
        explosion = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);

        // Prepare Separate Frames.

        // Projectile :
        iProjectileHeight = rotatedProjectile.getHeight();

        iProjectileWidth = rotatedProjectile.getWidth();

        projSourceRect = new Rect(0, 0, iProjectileWidth, iProjectileHeight);

        destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);

        // Explosion :
        iExplosionHeight = explosion.getHeight();

        iExplosionWidth = explosion.getWidth();

        expSourceRect = new Rect(0, 0, iExplosionWidth, iExplosionHeight);
    }

    //------------------------------------------------------------------------
    // This will be used to set the value of hasHit when the projectile hits a surface.
    public void setHasCollided(boolean hasHit)
    {
        bHasCollided = hasHit;
    }

    //------------------------------------------------------------------------
    // This will return the value of projectileX.
    public int getProjectileX()
    {
        return iProjectileXPos;
    }

    //------------------------------------------------------------------------
    // This will return the value of projectileY
    public int getProjectileY()
    {
        return iProjectileYPos;
    }

    //------------------------------------------------------------------------
    // This will be used to draw this projectile.
    public void drawProjectile(Canvas canvas)
    {
        if(!bHasCollided)
        {
            if (rotatedProjectile != null)
            {
                canvas.drawBitmap(rotatedProjectile, projSourceRect, destRect, null);
            }
            else
            {
                // Log.e(TAG, "(drawProjectile) Projectile : Unable to draw item.");
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
                // Log.e(TAG, "(drawProjectile) Explosion : Unable to draw item.");
            }
        }

    }

}
