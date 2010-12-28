package com.android.game.DoubleU;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.java.android.DoubleU.Algorithm.Point;
import com.java.android.DoubleU.Algorithm.Vector;
import com.java.android.DoubleU.Exceptions.MathException;

/**
 * @author Tue Bui
 *
 */
public class Contour {
	private List<Vector> vectorList;
	private Point currPoint;
	private int currLineIndex;

	public Contour() {
		vectorList = new ArrayList<Vector>();
		currLineIndex = -1;
	}

	private Point getCurrPoint() {
		if (this.currPoint == null) {
			if (this.vectorList.size() > 0) {
				return this.vectorList.get(currLineIndex).getStartPoint();
			} else
				return null;
		} else
			return this.currPoint;
	}

	public void appendLine(Point startPoint, Point endPoint) {
		appendLine(startPoint.getX(), startPoint.getY(), endPoint.getX(),
				endPoint.getY());
	}

	public void appendLine(float startX, float startY, float stopX, float stopY) {
		Vector aLine = new Vector(startX, startY, stopX, stopY);
		vectorList.add(aLine);
	}

	public void appendLine(Vector aV) {
		Vector aLine = new Vector(aV.getStartPoint(), aV.getStopPoint());
		vectorList.add(aLine);
	}

	public void drawMe(Canvas c, Paint p) {
		// int pointIndex = 0;
		for (int i = 0; i < this.vectorList.size(); i++) {
			Vector aVector = this.vectorList.get(i);
			aVector.drawMe(c, p);
		}
	}
	
	public Vector getNextVector() {
		if (currLineIndex == this.vectorList.size() - 1)
			return null; // out of range
		currLineIndex++;
		Vector result = this.vectorList.get(currLineIndex).clone();
		this.currPoint = result.getStartPoint();
		return result;
	}

	/*
	 * Get the current vector the finger has been started 
	 * or just finished.
	 */
	public Vector getCurrentVector() {
		if (this.currLineIndex == -1) {
			return this.getNextVector();
		}
		if (this.getCurrPoint() != null) {
			Vector currV = this.vectorList.get(currLineIndex).clone();
			// if the current point is the end of the vector
			// serve another one if any
			if (this.getCurrPoint().equals(currV.getStopPoint())) {
				return this.getNextVector();
			} else {
				return currV;
			}
		}
		return null;
	}
	
	/*
	 * get the lines inside circle 
	 */
	public Point[] getLinePartInsideCircle(Point point, float mR)
			throws MathException {
		// TODO Auto-generated method stub
		Vector currVector = this.getCurrentVector();
		Point[] result = new Point[0];
		int currIndex = this.currLineIndex;
		if (currVector != null) {
			result = currVector.getLinePartInsideCircle(point, mR);
			if (result.length == 2) {
				// check if the currPoint (the last point) in the middle
				// of those two intersections. We use the cross product
				// because at the time the 3 points are in a line
				Vector v1 = new Vector(this.getCurrPoint(), result[0]);
				Vector v2 = new Vector(this.getCurrPoint(), result[1]);
				float crossProduct = v1.crossProduct(v2);
				if (crossProduct > 0) {
					// the finger does not touch the point
					result = new Point[0];
				} else {
					crossProduct = currVector.crossProduct(v1);
					if (crossProduct > 0) {
						// currVector  and v1 have the same direction
						this.currPoint = result[0];
					} else {
						// v1 have different direction or point result[0]
						// is located at the current point.
						this.currPoint = result[1];
					}
					// after get the part of line that have been touched
					// update the current vector.
					this.getCurrentVector();
				}
			} else if (result.length == 1) {
				if (!result[0].equals(this.getCurrPoint()))
					result = new Point[0];
			} else {
				result = new Point[0];
			}

			if (this.currLineIndex == currIndex + 1){
				// just one line have been touched
				Point[] newPart = this.getLinePartInsideCircle(point, mR);
				if (newPart.length > 0){
					List<Point> newResult = new ArrayList<Point>();
					for (int i = 0; i < result.length; i++) {
						newResult.add(result[i]);
					}
					for (int i = 0; i < newPart.length; i++) {
						newResult.add(newPart[i]);
					}
					result = newResult.toArray(new Point[0]);
				}
			}
		}
		return result;
	}
}
