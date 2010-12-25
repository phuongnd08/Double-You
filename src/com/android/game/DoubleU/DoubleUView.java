package com.android.game.DoubleU;

import com.java.android.DoubleU.Algorithm.StraightLine;
import com.java.android.DoubleU.Exceptions.MathException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;
import android.view.View;

public class DoubleUView extends View {

    private Bitmap mLayoutBitmap;
    private Paint mBitmapPaint;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;

    private float mStartX;
    private float mStartY;
    private float mStopX;
    private float mStopY;
    private StraightLine mLine;

    private final float mR = 6f;

    public DoubleUView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        try {
            initLayoutBitmap();
        } catch (MathException e) {
            e.printStackTrace();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2 * mR);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }

    private void initLayoutBitmap() throws MathException {
        mLayoutBitmap = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
        mCanvas = new Canvas(mLayoutBitmap);
        Canvas c = new Canvas(mLayoutBitmap);
        c.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setColor(Color.BLUE);

        // draw a line in background
        this.mStartX = 50;
        this.mStartY = 50;
        this.mStopX = 200;
        this.mStopY = 300;
        this.mLine = new StraightLine(this.mStartX, this.mStartY,
                this.mStopX, this.mStopY);
        c.drawLine(this.mStartX, this.mStartY, this.mStopX, this.mStopY, p);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFAAAAAA);

        canvas.drawBitmap(mLayoutBitmap, 0, 0, mBitmapPaint);

        canvas.drawPath(mPath, mPaint);
    };

    private void highlightLine(float x, float y) throws MathException {
        com.java.android.DoubleU.Algorithm.Point[] intersections = mLine
                .getLinePartInsideCircle(new com.java.android.DoubleU.Algorithm.Point(x, y), mR);
        if (intersections.length == 2) {
            mPath.reset();
            mPath.moveTo(intersections[0].getX(), intersections[0].getY());
            mPath.lineTo(intersections[1].getX(), intersections[1].getY());
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        } else if (intersections.length == 1) {
            mCanvas.drawPoint(intersections[0].getX(), intersections[0].getY(),
                    mPaint);
        }
    }

    private void touch_start(float x, float y) throws MathException {
        highlightLine(x, y);
    }

    private void touch_move(float x, float y) throws MathException {
        highlightLine(x, y);
    }

    private void touch_up() {
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // record the current position of touch point
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            try {
                touch_start(x, y);
            } catch (MathException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            try {
                touch_move(x, y);
            } catch (MathException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            invalidate();
            break;
        case MotionEvent.ACTION_UP:
            touch_up();
            invalidate();
            break;
        }
        return true;
    }
}