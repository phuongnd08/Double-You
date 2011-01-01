package com.android.game.DoubleU;

import com.java.android.DoubleU.Exceptions.MathException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DoubleUView extends View {

    private Bitmap mLayoutBitmap;
    private Paint mBitmapPaint;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Contour mContour; 

    private final float mR = 8f;

    public void initDoubleUView() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        mLayoutBitmap = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
        mCanvas = new Canvas(mLayoutBitmap);        
        initContour();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        
    }

	public DoubleUView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initDoubleUView();
	}
	
    public void initContour(){
    	this.mContour = new Contour();
        // draw a line in background
        Canvas c = new Canvas(mLayoutBitmap);
        c.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        this.mContour.appendLine(50, 50, 120, 100);
        this.mContour.appendLine(120, 100, 150, 200);
        this.mContour.appendLine(150, 200, 50, 200);
        this.mContour.appendLine(50, 200, 50, 50);
        this.mContour.drawMe(c, p);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFAAAAAA);

        canvas.drawBitmap(mLayoutBitmap, 0, 0, mBitmapPaint);

        canvas.drawPath(mPath, mPaint);
    };

    private void drawIntersections(com.java.android.DoubleU.Algorithm.Point[] points){
    	if (points.length > 1){
			com.java.android.DoubleU.Algorithm.Point startPoint = points[0]; 
			com.java.android.DoubleU.Algorithm.Point stopPoint;
			int index = 1;
			do {
				stopPoint  = points[index]; 
				mPath.reset();
				mPath.moveTo(startPoint.getX(), startPoint.getY());
				mPath.lineTo(stopPoint.getX(), stopPoint.getY());
				mCanvas.drawPath(mPath, mPaint);
				mPath.reset();
				startPoint = stopPoint;
				index++;
			} while (index < points.length);
    	} else if (points.length == 1){
            mCanvas.drawPoint(points[0].getX(), points[0].getY(),
                    mPaint);
    	}
    }
    private void highlightLine(float x, float y) throws MathException {
        com.java.android.DoubleU.Algorithm.Point[] intersections = this.mContour
                .getLinePartInsideCircle(new com.java.android.DoubleU.Algorithm.Point(x, y), mR);
        this.drawIntersections(intersections);
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