package com.example.ryan.mobilegamesassignmentgalaga.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;
import android.os.Process;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ryan on 21/02/2018.
 */

public class GameView extends SurfaceView implements Runnable{

    // Constructor

    public GameView(Context context, Point screenS)
    {
        super(context);

        holder = getHolder();

        screenSize = screenS;

    }

    // Data Members

    Point screenSize;

    SurfaceHolder holder;

    private boolean ok = false;

    Thread t = null;

    Paint paint = new Paint();

    // Member Functions

    private void m_UpdateCanvas()
    {

    }

    protected void  m_DrawCanvas(Canvas canvas)
    {
        canvas.drawARGB(0, 0, 0, 0);


    }

    public void run()
    {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (ok)
        {
            if(!holder.getSurface().isValid())
            {
                continue;
            }

            Canvas canvas = holder.lockCanvas();

            this.m_UpdateCanvas();;

            this.draw(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void m_Pause()
    {
        ok = false;

        while(true)
        {
            try
            {
                t.join();
            }

            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            break;
        }

        t = null;

    }

    public void m_Resume()
    {
        ok = true;

        t = new Thread(this);

        t.start();

    }



}
