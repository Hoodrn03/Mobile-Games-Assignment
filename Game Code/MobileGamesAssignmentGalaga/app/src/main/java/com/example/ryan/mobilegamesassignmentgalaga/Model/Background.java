package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.ryan.mobilegamesassignmentgalaga.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 27/02/2018.
 */

public class Background {

    //------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------

    public Background()
    {

    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    private Bitmap backgroundImg;

    private Bitmap scaledBackground;

    // This will hold the x and y for the background.
    private int imageX, imageY;

    // This will hold the relative screen size of the device.
    private Point screenSize;

    //------------------------------------------------------------------------
    // Member Functions :
    //------------------------------------------------------------------------

    // This will be used to set the desired size of the background image.
    public void setImageSize(int newX, int newY)
    {
        imageX = newX;

        imageY = newY;

        Log.e(TAG, "Device size is : X - " + imageX + " Y -" + imageY);
    }

    // This will be used to select a background for the game.
    public void setBackground(Context context)
    {
        Resources res = context.getResources();

        backgroundImg = BitmapFactory.decodeResource(res, R.drawable.space1);

        scaledBackground = Bitmap.createScaledBitmap(backgroundImg, backgroundImg.getWidth(), imageY, false);

    }

    // This will draw the background on a given canvas.
    public void drawBackground(Canvas canvas) {

        if (scaledBackground != null) {

            canvas.drawBitmap(scaledBackground, 0, 0, null);

        }

        else
        {

            Log.d(TAG, "drawBackground : Unable to draw item.");

        }

    }



}
