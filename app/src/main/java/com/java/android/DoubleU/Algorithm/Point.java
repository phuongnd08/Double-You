package com.java.android.DoubleU.Algorithm;

public class Point {
    private float mX;
    private float mY;
    private float epsilon = 0.000001f;

    public Point(float x, float y) {
        this.mX = x;
        this.mY = y;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Point)) {
            return false;
        } else {
            Point aPoint = (Point) o;
            if (Math.abs(aPoint.mX - this.mX) < epsilon
                    && Math.abs(aPoint.mY - this.mY) < epsilon)
                return true;
            else
                return false;
        }
    }
}
