package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckerTest {

    //простые, что вообще проходит проверка на попадание
    @Test
    public void trueHitTest() {
        double x = -1;
        double y = 0.1;
        double r = 3;

        boolean expectedHit = true;
        boolean actualHit = Checker.checkHit(x, y, r);

        assertEquals(expectedHit, actualHit);
    }

    @Test
    public void missTest() {
        double x = 0.72;
        double y = 0.816;
        double r = 3;

        boolean expectedHit = false;
        boolean actualHit = Checker.checkHit(x, y, r);

        assertEquals(expectedHit, actualHit);
    }

    @Test
    public void validHitTest() {
        double x = -1;
        double y = 0.1;
        double r = 3;

        boolean expectedValidity = true;
        boolean actualValidity = Checker.getIsValid(x, y,r);


        assertEquals(expectedValidity, actualValidity);
    }

    //тесты на валидность x,y,r
    @Test
    public void invalidX() {
        double lowX = -52;
        double highX = 52;
        double y = 2.0;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(lowX, y, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(highX,y,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void invalidY() {
        double x = -1;
        double lowY = -52;
        double highY = 52;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, lowY, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x,highY,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void invalidR() {
        double x = -1;
        double y = 3.0;
        double lowR = 0.4;
        double highR = 52;
        //радиус физически не может быть меньше или равен 0
        double impossibleR = -52;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, y, lowR);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, highR);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, impossibleR);
        assertEquals(expectedValidity, actualValidity);
    }

    //тесты на граничные значения
    @Test
    public void borderX() {
        double lowBorderX1 = -5.00000000000001;
        double lowBorderX = -5;
        double lowBorderX2 = -4.99999999999999;

        double highBorderX1 = 3.00000000000001;
        double highBorderX = 3;
        double highBorderX2 = 2.99999999999999;
        double y = 2.0;
        double r = 3.0;

        assertFalse(Checker.getIsValid(lowBorderX1, y, r));
        assertFalse(Checker.getIsValid(lowBorderX, y, r));
        assertTrue(Checker.getIsValid(lowBorderX2, y, r));

        assertFalse(Checker.getIsValid(highBorderX1, y, r));
        assertFalse(Checker.getIsValid(highBorderX, y, r));
        assertTrue(Checker.getIsValid(highBorderX2, y, r));
    }

    @Test
    public void borderY() {
        double lowBorderY1 = -5.00000000000001;
        double lowBorderY = -5;
        double lowBorderY2 = -4.99999999999999;

        double highBorderY1 = 3.00000000000001;
        double highBorderY = 3;
        double highBorderY2 = 2.99999999999999;
        double x = -1;
        double r = 3.0;

        assertFalse(Checker.getIsValid(x, lowBorderY1, r));
        assertFalse(Checker.getIsValid(x, lowBorderY, r));
        assertTrue(Checker.getIsValid(x, lowBorderY2, r));

        assertFalse(Checker.getIsValid(x, highBorderY1, r));
        assertFalse(Checker.getIsValid(x, highBorderY, r));
        assertTrue(Checker.getIsValid(x, highBorderY2, r));
    }

    @Test
    public void borderR() {
        double x = -1;
        double y = 2.0;

        double lowBorderR1 = 0.99999999999999;
        double lowBorderR = 1;
        double lowBorderR2 = 1.000000000000001;

        double highBorderR1 = 4.00000000000001;
        double highBorderR = 4;
        double highBorderR2 = 2.99999999999999;

        assertFalse(Checker.getIsValid(x, y, lowBorderR1));
        assertFalse(Checker.getIsValid(x, y, lowBorderR));
        assertTrue(Checker.getIsValid(x, y, lowBorderR2));

        assertFalse(Checker.getIsValid(x, y, highBorderR1));
        assertFalse(Checker.getIsValid(x, y, highBorderR));
        assertTrue(Checker.getIsValid(x, y, highBorderR2));
    }
}