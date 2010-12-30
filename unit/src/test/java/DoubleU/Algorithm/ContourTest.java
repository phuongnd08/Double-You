package test.java.DoubleU.Algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import com.android.game.DoubleU.Contour;
import com.java.android.DoubleU.Algorithm.Point;
import com.java.android.DoubleU.Algorithm.Vector;
import com.java.android.DoubleU.Exceptions.MathException;

public class ContourTest {
	public float epsilon = 0.000001f;

	@Test
	public void testGetNextVector() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLinePartInsideCircle() throws MathException {
		Contour aContour = new Contour();
		Vector aLine1 = new Vector(-1, 0, 0, 2);
		Vector aLine2 = new Vector(0, 2, 0, 0);
		aContour.appendLine(aLine1);
		aContour.appendLine(aLine2);

		Point[] expected;
		// the line is tangent of the circle when
		// the line is highlighted
		Point aP = new Point(0, -0.5f);
		expected = aContour.getLinePartInsideCircle(aP, (float) Math
				.sqrt(1.25f));
		assertTrue(expected.length == 1);
		assertTrue(Math.abs(expected[0].getX() - -1f) < epsilon
				&& Math.abs(expected[0].getY() - 0f) < epsilon);

		// the finger touched one line and update the current point
		aP = new Point(-1, 1);
		expected = aContour.getLinePartInsideCircle(aP, 1);
		assertTrue(expected.length == 2);
		
		// the finger touched the line that have been highlighted
		aP = new Point(-0.5f, 0);
		expected = aContour.getLinePartInsideCircle(aP, 0.5f);
		assertTrue(expected.length == 0);
		
		// the finger touched the corner that connects lines
		aP = new Point(0, 2);
		expected = aContour.getLinePartInsideCircle(aP, 0.5f);
		assertTrue(expected.length == 4);
		
		// the finger touched the line but outside the target 
		aP = new Point(0, 0);
		expected = aContour.getLinePartInsideCircle(aP, 1.4f);
		assertTrue(expected.length == 0);
	}
	@Test
	public void testGetLinePartInsideCircle2() throws MathException {
		Contour aContour = new Contour();
		Vector aLine1 = new Vector(50, 50, 150, 200);
		aContour.appendLine(aLine1);
		//aContour.appendLine(aLine2);

		Point[] expected;
		// the line is tangent of the circle when
		// the line is highlighted
		Point aP = new Point(51, 51);
		expected = aContour.getLinePartInsideCircle(aP, 6);
		assertTrue(expected.length == 2);
//		assertTrue(Math.abs(expected[0].getX() - -1f) < epsilon
//				&& Math.abs(expected[0].getY() - 0f) < epsilon);

	}
}
