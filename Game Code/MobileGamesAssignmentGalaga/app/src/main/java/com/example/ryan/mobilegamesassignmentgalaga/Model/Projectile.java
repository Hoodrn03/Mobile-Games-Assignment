package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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

    private Rect sourceRect;

    private Rect destRect;

    private int iProjectileHeight, iProjectileWidth;

    private int iRow = 2;
    private int iColumn = 2;

    private int iColumnCount = 0;
    private int iRowCount = 0;

    private boolean bHasCollided = false;

    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    public void updateProjectile()
    {

        iProjectileYPos += iProjectileSpeed;

        // Log.e(TAG, "(Update Projectile) Projectile Pos : " + iProjectileXPos + " " + iProjectileYPos);

        destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);

        if(iProjectileYPos <= 0)
        {
            bHasCollided = true;
        }

    }

    public boolean getHasCollided()
    {
        return bHasCollided;
    }

    public void setProjectile(Context context)
    {
        // Set Bitmaps.

        Resources res = context.getResources();

        projectile = BitmapFactory.decodeResource(res, R.drawable.missile);

        projectileToDraw = Bitmap.createScaledBitmap(projectile, projectile.getWidth() / 4, projectile.getHeight() / 8, false);

        // Prepare Separate Frames.

        iProjectileHeight = projectileToDraw.getHeight();

        iProjectileWidth = projectileToDraw.getWidth();

        sourceRect = new Rect(0, 0, iProjectileWidth, iProjectileHeight);

        destRect = new Rect(iProjectileXPos, iProjectileYPos, iProjectileXPos + iProjectileHeight, iProjectileYPos + iProjectileWidth);
    }

    public void drawProjectile(Canvas canvas)
    {

        if(projectileToDraw != null)
        {
            canvas.drawBitmap(projectileToDraw, sourceRect, destRect, null);
        }
        else
        {
            Log.e(TAG, "(drawProjectile) Unable to draw item.");
        }

    }

}
