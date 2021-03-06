package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 28/02/2018.
 */

public class InputButton {

    //------------------------------------------------------------------------
    // Constructor :
    //------------------------------------------------------------------------

    InputButton()
    {
    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    // This will be used to check for the player's input.
    private Rect inputZone;

    private Paint paint;

    private int buttonPosX, buttonPosY;

    private int buttonHeight, buttonWidth;

    //------------------------------------------------------------------------
    // Member Functions  :
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // This will be used to check if the given coordinates are within this input button.
    public boolean checkZone(int pressedX, int pressedY)
    {
        if(pressedX < inputZone.left && pressedY < inputZone.top)
        {
            if(pressedX > inputZone.right && pressedY > inputZone.bottom)
            {
                // Log.e(TAG, "(Check Zone) Within Zone");

                return true;
            }
        }

        return false;
    }

    //------------------------------------------------------------------------
    // This will be used to set the bounds of this input button.
    public void setZoneBounds(int newX, int newY, int newWidth, int newHeight, Paint newPaint)
    {

        buttonPosX = newX;
        buttonPosY = newY;

        buttonWidth = newWidth;
        buttonHeight = newHeight;

        // Log.e(TAG, "(Set Zone Bounds) " + buttonPosX + "  " + buttonPosY);

        inputZone = new Rect(buttonWidth, buttonHeight, buttonPosX, buttonPosY);

        paint = new Paint();

        paint = newPaint;
        paint.setStrokeWidth(1);
        paint.setAlpha(50);

    }

    //------------------------------------------------------------------------
    // This will be used to draw this input button.
    public void drawZone(Canvas canvas)
    {
        canvas.drawRect(inputZone, paint);
    }

}
