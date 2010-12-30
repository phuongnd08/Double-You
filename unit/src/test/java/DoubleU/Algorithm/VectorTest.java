package test.java.DoubleU.Algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.android.DoubleU.Algorithm.Point;
import com.java.android.DoubleU.Algorithm.Vector;
import com.java.android.DoubleU.Exceptions.MathException;

public class VectorTest {
	public float epsilon = 0.000001f;

    @Test
    public void testGetGradient() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 1);
        float actual = aLine.getGradient();
        assertTrue(Math.abs(actual - 1) < epsilon);
        assertEquals(1, actual, epsilon);
    }

    @Test
    public void testGetGradientThrowException() throws MathException {
        Vector aLine = new Vector(-1, 0, -1, 1);
        try {
            aLine.getGradient();
        } catch (MathException e) {
            return;
        }
        fail("Supposed to throw MathException here.");
    }

    @Test
    public void testGetConstantTerm() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 2);
        float actual = aLine.getConstantTerm();
        assertEquals(2, actual, epsilon);
    }

    @Test
    public void testGetConstantTermThrowException() {
        Vector aLine = new Vector(-1, 0, -1, 1);
        try {
            aLine.getConstantTerm();
        } catch (MathException e) {
            return;
        }
        fail("Supposed to throw MathException here.");
    }

    @Test
    public void test2Points_GetCircleIntersection() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 2);
        Point[] expected;
        Point aP = new Point(-1, 1);
        expected = aLine.getCircleIntersection(aP, 1);
        assertTrue(expected.length == 2);
        assertTrue(Math.abs(expected[0].getX() - -1) < epsilon
                || Math.abs(expected[1].getX() - -1) < epsilon);
        assertTrue(Math.abs(expected[0].getY() - 1.6f) < epsilon
                || Math.abs(expected[1].getY() - 1.6f) < epsilon);
    }

    @Test
    public void test1Points_GetCircleIntersection() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 2);
        Point[] expected;
        Point aP = new Point(0, -0.5f);
        expected = aLine.getCircleIntersection(aP, (float) Math.sqrt(1.25f));
        assertTrue(expected.length == 1);
        assertTrue(Math.abs(expected[0].getX() - -1f) < epsilon
                && Math.abs(expected[0].getY() - 0f) < epsilon);
    }

    @Test
    public void test2PointGetCircle_VerticalLineIntersection()
            throws MathException {
        Vector aLine = new Vector(1, 0, 1, 5);
        Point[] expected;
        Point aP = new Point(0, 1);
        expected = aLine.getCircleIntersection(aP, (float) Math.sqrt(2f));
        assertTrue(expected.length == 2);
        assertTrue(Math.abs(expected[0].getX() - 1f) < epsilon
                || Math.abs(expected[1].getX() - 1f) < epsilon);
        assertTrue(Math.abs(expected[0].getY() - 2f) < epsilon
                || Math.abs(expected[1].getY() - 2f) < epsilon);
    }

    @Test
    public void test1PointGetCircle_VerticalLineIntersection()
            throws MathException {
        Vector aLine = new Vector(1, 0, 1, 5);
        Point[] expected;
        Point aP = new Point(0, 0);
        expected = aLine.getCircleIntersection(aP, 1f);
        assertTrue(expected.length == 1);
        assertEquals(expected[0].getX(), 1f, epsilon);
        assertEquals(expected[0].getY(), 0, epsilon);
    }
    @Test
    public void test0PointGetCircle_LineIntersection() throws MathException{
        Vector aLine = new Vector(1, 0, 1, 5);
        Point[] expected;
        Point aP = new Point(0, 0);
        expected = aLine.getCircleIntersection(aP, 0.5f);
        assertTrue(expected.length == 0);
        
        aLine = new Vector(-1, 0, 0, 2);
        aP = new Point(0, -0.5f);
        expected = aLine.getCircleIntersection(aP, (float) Math.sqrt(1.2f));
        assertTrue(expected.length == 0);
    }
    @Test
    public void testPointInLine() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 2);
        assertFalse(aLine.isInsideTheLine(0, 0));
        assertFalse(aLine.isInsideTheLine(1, 4));
        assertTrue(aLine.isInsideTheLine(-0.5f, 1));
    }
    @Test
    public void test1PointGetOverlappingLine() throws MathException{
         Point[] actual;
         Vector aLine = new Vector(1,2,3,4);
         Point point1 = new Point(0,0);
         Point point2 = new Point(1, (float) Math.sqrt(2));
         Point point3 = new Point(1, (float) Math.sqrt(2));
         Point point4 = new Point(3, (float) Math.sqrt(2)*3);
         actual = aLine.getOverlappingLine(point1, point2, point3 , point4);
         assertEquals(actual.length, 1);
         assertEquals(point2 , actual[0]);
         
         actual = aLine.getOverlappingLine(point3, point4, point1 , point2);
         assertEquals(actual.length, 1);
         assertEquals(point2 , actual[0]);
    }
    @Test
    public void test0PointGetOverlappingLine() throws MathException {
        Point[] actual;
        Vector aLine = new Vector(1, 2, 3, 4);
        Point point1 = new Point(0,0);
        Point point2 = new Point(1, (float) Math.sqrt(2));
        Point point3 = new Point(2, (float) Math.sqrt(2)*2);
        Point point4 = new Point(3, (float) Math.sqrt(2)*3);
        actual = aLine.getOverlappingLine(point1, point2, point3 , point4);
        assertEquals(actual.length, 0);
        
        actual = aLine.getOverlappingLine(point3, point4, point1 , point2);
        assertEquals(actual.length, 0);
    }
    
    @Test
    public void test2PointGetOverlappingLine1() throws MathException {
        Point[] actual;
        Vector aLine = new Vector(1, 2, 3, 4);
        Point point1 = new Point(0,0);
        Point point2 = new Point(2, (float) Math.sqrt(2)*2);
        Point point3 = new Point(1, (float) Math.sqrt(2));
        Point point4 = new Point(3, (float) Math.sqrt(2)*3);
        actual = aLine.getOverlappingLine(point1, point2, point3 , point4);
        assertEquals(actual.length, 2);
        Point temp = aLine.getMinXPoint(actual[0], actual[1]);
        assertEquals(point3 , temp);
        temp = aLine.getMaxXPoint(actual[0], actual[1]);
        assertEquals(point2 , temp);
        
        actual = aLine.getOverlappingLine(point3, point4, point1 , point2);
        assertEquals(actual.length, 2);
        temp = aLine.getMinXPoint(actual[0], actual[1]);
        assertEquals(point3 , temp);
        temp = aLine.getMaxXPoint(actual[0], actual[1]);
        assertEquals(point2 , temp);        
    }    
    
    @Test
    public void test2PointGetOverlappingLine2() throws MathException {
        Point[] actual;
        Vector aLine = new Vector(1, 2, 3, 4);
        Point point1 = new Point(0,0);
        Point point2 = new Point(3, (float) Math.sqrt(2)*3);        
        Point point3 = new Point(2, (float) Math.sqrt(2)*2);
        Point point4 = new Point(1, (float) Math.sqrt(2));

        actual = aLine.getOverlappingLine(point1, point2, point3 , point4);
        assertEquals(actual.length, 2);
        Point temp = aLine.getMinXPoint(actual[0], actual[1]);
        assertEquals(point4 , temp);
        temp = aLine.getMaxXPoint(actual[0], actual[1]);
        assertEquals(point3 , temp);
    }    
    
    public void testGetLinePartInsideCircle() throws MathException {
        Vector aLine = new Vector(-1, 0, 0, 2);
        Point[] expected;
        Point aP = new Point(0, -0.5f);
        expected = aLine.getLinePartInsideCircle(aP, (float) Math.sqrt(1.25f));
        assertEquals(expected.length, 1);
        assertEquals(expected[0], new Point(-1,0));
    }
	@Test
	public void testGetCoordinateX() {
		Vector aLine = new Vector(-1, 0, 0, 2);
		assertEquals(1, aLine.getCoordinateX(), epsilon);
	}

	@Test
	public void testGetCoordinateY() {
		Vector aLine = new Vector(-1, 0, 0, 2);
		assertEquals(2, aLine.getCoordinateY(), epsilon);
	}

	@Test
	public void testClone() {
		Vector aLine = new Vector(-1, 0, 0, 2);
		Vector aV = aLine.clone();
		assertEquals(1, aV.getCoordinateX(), epsilon);
		assertEquals(2, aV.getCoordinateY(), epsilon);
	}

	@Test
	public void testCrossProduct() {
		Vector aLine = new Vector(-1, 0, 0, 2);
		Vector aV 	 = new Vector(-1, 0, 0, -0.5f);
		assertEquals(aLine.crossProduct(aV), 0, epsilon);
	}

}
