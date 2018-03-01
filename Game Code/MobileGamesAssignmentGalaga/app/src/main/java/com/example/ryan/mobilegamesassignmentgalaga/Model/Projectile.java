package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public Projectile(int newXPos, int newYPos)
    {

        iProjectileXPos = newXPos;

        iProjectileYPos = newYPos;

    }

    //------------------------------------------------------------------------
    // Data Members
    //------------------------------------------------------------------------

    private int iProjectileXPos, iProjectileYPos;

    private int iProjectileSpeed = 25;

    private Bitmap projectile;

    private Bitmap explosion;

    private Rect sourceRect;

    private Rect destRect;

    int iProjectileHeight, iProjectileWidth;

    int iRow = 2;
    int iColumn = 2;

    int iColumnCount = 0;
    int iRowCount = 0;

    boolean hasCollided = false;

    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    public void updateProjectile()
    {

        iProjectileYPos += iProjectileSpeed;

        Log.e(TAG, "Projectile Pos : " + iProjectileXPos + " " + iProjectileYPos);

    }

    public void setProjectile(Context context)
    {
        // Set Bitmaps.

        Resources res = context.getResources();

        projectile = BitmapFactory.decodeResource(res, R.drawable.missile);

        explosion = BitmapFactory.decodeResource(res, R.drawable.explosion);

        // Prepare Separate Frames.




    }

}
