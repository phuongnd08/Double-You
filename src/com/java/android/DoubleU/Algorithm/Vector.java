package com.java.android.DoubleU.Algorithm;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.java.android.DoubleU.Exceptions.MathException;

public class Vector {
    private float mGrad; // Gradient
    private float mTerm; // Term

    private float mStartX;
    private float mStartY;
    private float mStopX;
    private float mStopY;
    
    public final boolean mIsVerticalLine;
	private float epsilon = 0.01f;

    public Vector(float startX, float startY, float stopX, float stopY)
             {
        this.mStartX = startX;
        this.mStartY = startY;
        this.mStopX = stopX;
        this.mStopY = stopY;
        
        this.mIsVerticalLine = (startX == stopX);
        if (!this.mIsVerticalLine) {
            try {
				this.mGrad = getGradient();
				this.mTerm = getConstantTerm();
			} catch (MathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public Point getStartPoint(){
    	return new Point(this.mStartX, this.mStartY);
    }
    
    public Point getStopPoint(){
    	return new Point(mStopX, mStopY);
    }
    
    public float getCoordinateX(){
    	return this.mStopX - this.mStartX;
    }
    
    public float getCoordinateY(){
    	return this.mStopY - this.mStartY;
    }
    
    
    public Vector(Point X, Point Y) {
        this(X.getX(), X.getY(), Y.getX(), Y.getY());
    }

    public float getGradient() throws MathException {
        if (mIsVerticalLine) {
            throw new MathException("this is a vertical line.");
        }
        mGrad = (mStartY - mStopY) / (mStartX - mStopX);
        return mGrad;
    }

    public float getConstantTerm() throws MathException {
        if (mIsVerticalLine) {
            throw new MathException("this is a vertical line.");
        }
        mTerm = (mStartX * mStopY - mStopX * mStartY) / (mStartX - mStopX);
        return mTerm;
    }

    public Point[] getCircleIntersection(Point X, float R) {
        if (!mIsVerticalLine) {
            float a = (this.mGrad * this.mGrad + 1);
            float b = 2 * ((this.mTerm * this.mGrad - this.mGrad * X.getY() - X
                    .getX()));
            float c = (this.mTerm - X.getY()) * (this.mTerm - X.getY())
                    + X.getX() * X.getX() - R * R;
            float sqrDelta = b * b - 4 * a * c;

            if (sqrDelta > 0) {
                float delta = (float) Math.sqrt(b * b - 4 * a * c);
                float x1 = (-b + delta) / (2 * a);
                float x2 = (-b - delta) / (2 * a);
                Point[] result = new Point[2];
                result[0] = new Point(x1, x1 * this.mGrad + this.mTerm);
                result[1] = new Point(x2, x2 * this.mGrad + this.mTerm);
                return result;
            } else if (sqrDelta == 0) {
                Point[] result = new Point[1];
                float x1 = (-b) / (2 * a);
                result[0] = new Point(x1, x1 * this.mGrad + this.mTerm);
                return result;
            } else {
                return new Point[0];
            }
        } else {
            // the line is vertical: x = a0
            float sqrDelta = R * R - (this.mStartX - X.getX())
                    * (this.mStartX - X.getX());
            if (sqrDelta > 0) {
                float delta = (float) Math.sqrt(sqrDelta);
                float y1 = X.getY() - delta;
                float y2 = X.getY() + delta;
                Point[] result = new Point[2];
                result[0] = new Point(this.mStartX, y1);
                result[1] = new Point(this.mStartX, y2);
                return result;
            } else if (sqrDelta == 0) {
                Point[] result = new Point[1];
                result[0] = new Point(this.mStartX, X.getY());
                return result;
            } else {
                return new Point[0];
            }
        }
    }

    public boolean isInsideTheLine(Point x) throws MathException {
        return isInsideTheLine(x.getX(), x.getY());
    }

    public boolean isInsideTheLine(float x, float y) throws MathException {
        if (!isOnTheLine(x, y)) {
            // the point not on the line
            return false;
        } else {
            // the cross product is less than 0 means the point
            // is inside the line
            float crossProduct = (x - this.mStartX) * (x - this.mStopX)
                    + (y - this.mStartY) * (y - this.mStopY);
            return (crossProduct <= 0);
        }
    }

    public boolean isOnTheLine(float x, float y){
        return Math.abs((x - this.mStartX) * (y - this.mStopY) - (x - this.mStopX)
                * (y - this.mStartY)) < epsilon ;
    }

    public boolean isOnTheLine(Point x){
        return isOnTheLine(x.getX(), x.getY());
    }
    
    public Point getMaxXPoint(Point point1, Point point2) {
        if (point1 == null)
            return point2;
        if (point2 == null)
            return point1;
        return (point1.getX() >= point2.getX()) ? point1 : point2;
    }
    
    public Point getMinXPoint(Point point1, Point point2) {
        if (point1 == null)
            return point2;
        if (point2 == null)
            return point1;
        return (point1.getX() <= point2.getX()) ? point1 : point2;
    }
    
    /*
     *  If the line is not vertical, we can use the x coordinate
     *  to calculate the overlapping line. If the line is vertical
     *  just convert Y value to X and do the same function
     */
    private Point[] getOverlappingLineByXCoordinate(Point startLine1, Point stopLine1,
            Point startLine2, Point stopLine2){
    	Point[] result;
        // 2 intersections now are on the line
        Point minXLine1 = getMinXPoint(startLine1, stopLine1);
        Point maxXLine1 = getMaxXPoint(startLine1, stopLine1);
        Point minXLine2 = getMinXPoint(startLine2, stopLine2);
        Point maxXLine2 = getMaxXPoint(startLine2, stopLine2);
        
        if (minXLine1.getX() < minXLine2.getX()) {
            if (maxXLine1.getX() < minXLine2.getX()) {
                result = new Point[0];
            } else{
                result = new Point[2];
                result[0] = minXLine2;
                if (minXLine2.getX() <= maxXLine1.getX()
                        && maxXLine1.getX() <= maxXLine2.getX()) {
                    result[1] = maxXLine1;
                } else {
                    result[1] = maxXLine2;
                }
            }
        } else if (minXLine2.getX() <= minXLine1.getX() && minXLine1.getX() <= maxXLine2.getX()){
            result = new Point[2];
            result[0] = minXLine1;                
            if (maxXLine1.getX() < maxXLine2.getX()){
                result[1] = maxXLine1;
            } else {
                result[1] = maxXLine2;
            }
        } else {
            result = new Point[0];
        }
        if (result.length == 2){
            if (result[0].equals(result[1])){
                Point temp = result[0];
                result = new Point[1];
                result[0] = temp;
            }
        }
        return result;
    }
    private Point getSymmetricalPoint(Point point){
    	return new Point(point.getY(), point.getX());
    }
    public Point[] getOverlappingLine (Point startLine1, Point stopLine1,
            Point startLine2, Point stopLine2){
        Point[] result = new Point[0];
        Vector v1 = new Vector(startLine1, stopLine1);
        if(v1.isOnTheLine(startLine2) && v1.isOnTheLine(stopLine2)){
        	if (v1.mIsVerticalLine){
        		startLine1 = this.getSymmetricalPoint(startLine1);
        		startLine2 = this.getSymmetricalPoint(startLine2);
        		stopLine1  = this.getSymmetricalPoint(stopLine1);
        		stopLine2  = this.getSymmetricalPoint(stopLine2);
        		result = this.getOverlappingLineByXCoordinate(startLine1, stopLine1, startLine2, stopLine2);
        		for (int i=0; i<result.length; i++){
        			result[i] = this.getSymmetricalPoint(result[i]);
        		}
        	} else {
        		result = this.getOverlappingLineByXCoordinate(startLine1, stopLine1, startLine2, stopLine2);
        	}
        }
        return result;
    }
    public Point[] getLinePartInsideCircle(Point X, float R)
            throws MathException {
        Point[] intersections = this.getCircleIntersection(X, R);
        Point[] result = new Point[0];
        if (intersections.length == 2) {
            // 2 intersections now are on the line
            result = this.getOverlappingLine(new Point(this.mStartX, this.mStartY), 
                    new Point(this.mStopX, this.mStopY), intersections[0], intersections[1]);
        } else if (intersections.length == 1) {
            if (this.isInsideTheLine(intersections[0])) {
                result = intersections;
            } 
        } 
        return result;
    }

    public Vector clone() {
    	// TODO Auto-generated method stub
    	Vector copied = new Vector(this.mStartX, 
    			this.mStartY, this.mStopX, this.mStopY );
    	return copied;
    }

	public void drawMe(Canvas c, Paint p) {
		// TODO Auto-generated method stub
		c.drawLine(mStartX, mStartY, mStopX, mStopY, p);
	}
	
	public float crossProduct (Vector aV){
		return this.getCoordinateX() * aV.getCoordinateX()
				+ this.getCoordinateY() * aV.getCoordinateY();
	}
}
