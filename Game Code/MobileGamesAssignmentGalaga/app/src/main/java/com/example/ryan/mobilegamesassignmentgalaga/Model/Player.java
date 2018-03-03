package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;

import com.example.ryan.mobilegamesassignmentgalaga.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 24/02/2018.
 */

// This will hold all of the items relating to the player. and the player's functionality.

public class Player {

    //------------------------------------------------------------------------
    // Constructor :
    //------------------------------------------------------------------------

    Player(int newYPos)
    {
        iPlayerPosY = newYPos;
    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    // This will be the player's base sprite.
    private Bitmap player;

    // This will be the player's edited sprite for drawing.
    private Bitmap playerToDraw;

    // These will hold the sprite's x and y coordinates.
    private int iPlayerPosX,iPlayerPosY;

    // This will hold the player's height and width for each sprite.
    private int iPlayerHeight, iPlayerWidth;

    // This will be used to determine how quickly the player moves along the screen.
    private int iPlayerSpeed = 15;

    // This will hold the sprite which is taken from the sprite sheet.
    private Rect sourceRect;

    // This will hold where the sprite is going to be drawn on the screen.
    private Rect destRect;

    // This will hold the position on the sprite sheet for the item to be drawn.
    private int iRow = 3;
    private int iColumn = 2;

    // This will hold the current position on the sprite sheet.
    private int iRowCounter = 0;
    private int iColumnCounter = 0;

    // These will be used to constrain the player to the device screen.
    private int iLeftBounds, iRightBounds;

    private float fCurrTime, fPrevTime, fDeltaTime, fUpdateTimer;

    //------------------------------------------------------------------------
    // Member Functions :
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // This will be used to update the player.
    public void updatePlayer() {

        fCurrTime = SystemClock.elapsedRealtime();

        fDeltaTime = fCurrTime - fPrevTime;

        fUpdateTimer += fDeltaTime;

        // Log.e(TAG, "(Update Player) DELTA TIME : " + fDeltaTime + " Current Timer : " + fUpdateTimer);

        if(fUpdateTimer >= 200)
        {
            frameToDraw();

            sourceRect.left = iColumnCounter * iPlayerWidth;
            sourceRect.right = sourceRect.left + iPlayerWidth;
            sourceRect.top = iRowCounter * iPlayerHeight;
            sourceRect.bottom = sourceRect.top + iPlayerHeight;

            fUpdateTimer = 0;

        }

        // Log.d(TAG, "(Update Player) Left : " + sourceRect.left + " Right : " + sourceRect.right + " Top : " + sourceRect.top + " Bottom : " + sourceRect.bottom);

        fPrevTime = fCurrTime;

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
    // This will be used to set the player's bitmap.
    public void setPlayer(Context context)
    {
        // Load Player Sprite sheet.

        Resources res = context.getResources();

        player = BitmapFactory.decodeResource(res, R.drawable.player);

        playerToDraw = Bitmap.createScaledBitmap(player, player.getWidth() * 2, player.getHeight() * 2, false);

        // Prepare for separate frames.

        iPlayerHeight = playerToDraw.getHeight() / iRow;

        iPlayerWidth = playerToDraw.getWidth() / iColumn;

        sourceRect = new Rect(0, 0, iPlayerWidth, iPlayerHeight);

        destRect = new Rect(iPlayerPosX, iPlayerPosY, iPlayerPosX + iPlayerWidth, iPlayerPosY + iPlayerHeight);

    }

    //------------------------------------------------------------------------
    // This will be used to move the player's sprite within the game world.
    public void movePlayer(boolean moveLeft)
    {
        if(moveLeft)
        {
            if(iPlayerPosX > iLeftBounds)
            {
                iPlayerPosX = iPlayerPosX - iPlayerSpeed;
            }
        }

        else
        {
            if(iPlayerPosX + iPlayerWidth < iRightBounds)
            {
                iPlayerPosX = iPlayerPosX + iPlayerSpeed;
            }
        }

        // Log.e(TAG,  "(Move Player) " + iPlayerPosX + " " + iPlayerPosY);

        destRect = new Rect(iPlayerPosX, iPlayerPosY, iPlayerPosX + iPlayerWidth, iPlayerPosY + iPlayerHeight);
    }

    //------------------------------------------------------------------------
    // This will be used to draw the player.
    public void drawPlayer(Canvas canvas)
    {
        if(playerToDraw != null)
        {
            canvas.drawBitmap(playerToDraw, sourceRect, destRect, null);
        }
        else
        {
            Log.e(TAG, "(drawPlayer) Unable to draw item.");
        }
    }

    //------------------------------------------------------------------------
    // This will be used to get the player's x possition.
    public int getPlayerX()
    {
        return iPlayerPosX + (iPlayerWidth / 2);
    }

    //------------------------------------------------------------------------
    // This will be used to get the player's y possition.
    public int getPlayerY()
    {
        return iPlayerPosY;
    }


}
