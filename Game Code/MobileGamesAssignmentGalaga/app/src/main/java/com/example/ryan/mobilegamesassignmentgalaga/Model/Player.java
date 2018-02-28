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

    Player()
    {

    }

    //------------------------------------------------------------------------
    // Data Members :
    //------------------------------------------------------------------------

    // This will be the player's base sprite.
    private Bitmap player;

    // This will be the player's edited sprite for drawing.
    private Bitmap playerToDraw;

    // These will hold the sprite's x and y coordinates.
    private int playerPosX, playerPosY;

    // This will hold the player's height and width for each sprite.
    private int playerHeight, playerWidth;

    // This will be used to determine how quickly the player moves along the screen.
    private int playerSpeed = 25;

    // This will hold the sprite which is taken from the sprite sheet.
    private Rect sourceRect;

    // This will hold where the sprite is going to be drawn on the screen.
    private Rect destRect;

    // This will hold the position on the sprite sheet for the item to be drawn.
    private int row = 3;
    private int column = 2;

    // This will hold the current position on the sprite sheet.
    private int rowCounter = 0;
    private int columnCounter = 0;


    //------------------------------------------------------------------------
    // Member Functions :
    //------------------------------------------------------------------------

    // This will be used to update the player.
    public void updatePlayer() {

        frameToDraw();

        sourceRect.left = columnCounter * playerWidth;
        sourceRect.right = sourceRect.left + playerWidth;
        sourceRect.top = rowCounter * playerHeight;
        sourceRect.bottom = sourceRect.top + playerHeight;

        // Log.d(TAG, "Left : " + sourceRect.left + " Right : " + sourceRect.right + " Top : " + sourceRect.top + " Bottom : " + sourceRect.bottom);



    }

    // This will be used to keep track of which item on the sprite sheet to draw.
    public void frameToDraw()
    {

        if(rowCounter >= row)
        {
            rowCounter = 0;

            columnCounter++;
        }

        if(columnCounter >= column)
        {
            columnCounter = 0;
        }

        rowCounter++;

    }

    // This will be used to set the player's bitmap.
    public void setPlayer(Context context)
    {
        // Load Player Sprite sheet.

        Resources res = context.getResources();

        player = BitmapFactory.decodeResource(res, R.drawable.player);

        playerToDraw = Bitmap.createScaledBitmap(player, player.getWidth() * 2, player.getHeight() * 2, false);

        // Prepare for separate frames.

        playerHeight = playerToDraw.getHeight() / row;

        playerWidth = playerToDraw.getWidth() / column;

        sourceRect = new Rect(0, 0, playerWidth, playerHeight);

        destRect = new Rect(playerPosX, playerPosY, playerPosX + playerWidth, playerPosY + playerHeight);

    }

    // This will be used to move the player's sprite within the game world.
    public void movePlayer(boolean moveLeft)
    {
        if(moveLeft)
        {
            playerPosX = playerPosX - playerSpeed;
        }

        else
        {
            playerPosX = playerPosX + playerSpeed;
        }

        playerPosY = 200;

        Log.e(TAG, playerPosX + " " + playerPosY);

        destRect = new Rect(playerPosX, playerPosY, playerPosX + playerWidth, playerPosY + playerHeight);
    }

    // This will be used to draw the player.
    public void drawPlayer(Canvas canvas)
    {
        if(playerToDraw != null)
        {
            canvas.drawBitmap(playerToDraw, sourceRect, destRect, null);
        }
        else
        {
            Log.d(TAG, "drawPlayer : Unable to draw item.");
        }
    }


}
