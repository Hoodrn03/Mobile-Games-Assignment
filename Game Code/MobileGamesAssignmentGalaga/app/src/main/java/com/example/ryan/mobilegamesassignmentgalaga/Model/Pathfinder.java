package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 06/03/2018.
 */

public class Pathfinder {

    //------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------
/*

    Pathfinder(int rightBounds, int bottomBounds)
    {

        this.generatePath(rightBounds, bottomBounds);
    }

*/
    //------------------------------------------------------------------------
    // Data members
    //------------------------------------------------------------------------

    private Path path;

    Paint paint;

    private int iRightBounds, iBottomBounds;

    ArrayList<Point> a_Points;

    int iCurrentPoint = 0;

    //------------------------------------------------------------------------
    // Member Functions
    //------------------------------------------------------------------------

    public void generatePath(int rightBounds, int bottomBounds)
    {
        // Prepare things.

        path = new Path();

        paint = new Paint();

        paint.setColor(Color.YELLOW);

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(10);

        paint.setARGB(100, 255, 100, 100);

        a_Points = new ArrayList<Point>();

        // Start using.

        iRightBounds = rightBounds;

        iBottomBounds = bottomBounds;

        Random r = new Random();

        path.moveTo(iRightBounds, r.nextInt(iBottomBounds));

        for(int i = 0; i < 10; i++)
        {
            Point currPoint = new Point();

            currPoint.x = r.nextInt(iRightBounds);

            currPoint.y = r.nextInt(iBottomBounds);

            a_Points.add(currPoint);

            a_Points.trimToSize();

            path.lineTo(currPoint.x, currPoint.y);

            // Log.e(TAG, "(Generate Path) " + i + " : Current Cords - X = " + a_Points.get(i).x + " Y: " + a_Points.get(i).y);
        }
    }

    public int moveAlongPathX(int currX, int moveSpeed)
    {
        if (iCurrentPoint < a_Points.size() )
        {

            if (currX < a_Points.get(iCurrentPoint).x)
            {

                currX += moveSpeed;

                if(currX > a_Points.get(iCurrentPoint).x)
                {
                    iCurrentPoint++;
                }
            }

            else if (currX > a_Points.get(iCurrentPoint).x)
            {
                currX -= moveSpeed;

                if(currX < a_Points.get(iCurrentPoint).x)
                {
                    iCurrentPoint++;
                }
            }

        }
        return currX;
    }

    public int moveAlongPathY(int currY, int moveSpeed)
    {
        if(iCurrentPoint < a_Points.size() )
        {
            if (currY < a_Points.get(iCurrentPoint).y)
            {
                currY += moveSpeed;

                if(currY > a_Points.get(iCurrentPoint).x)
                {
                    iCurrentPoint++;
                }
            }

            else if (currY > a_Points.get(iCurrentPoint).y)
            {
                currY -= moveSpeed;

                if(currY < a_Points.get(iCurrentPoint).x)
                {
                    iCurrentPoint++;
                }
            }

        }
        return currY;
    }

    public boolean endOfPath()
    {
        if(iCurrentPoint >= a_Points.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void drawPath(Canvas canvas)
    {
        canvas.drawPath(path, paint);
    }

}
