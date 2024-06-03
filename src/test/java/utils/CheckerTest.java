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
        double low_x = -52;
        double high_x = 52;
        double y = 2.0;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(low_x, y, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(high_x,y,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void invalidY() {
        double x = -1;
        double low_y = -52;
        double high_y = 52;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, low_y, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x,high_y,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void invalidR() {
        double x = -1;
        double y = 3.0;
        double low_r = 0.4;
        double high_r = 52;
        //радиус физически не может быть меньше или равен 0
        double impossible_r = -52;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, y, low_r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, high_r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, impossible_r);
        assertEquals(expectedValidity, actualValidity);
    }

    //тесты на граничные значения
    @Test
    public void borderX() {
        double low_x = -52;
        double high_x = 52;
        double y = 2.0;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(low_x, y, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(high_x,y,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void borderY() {
        double x = -1;
        double low_y = -52;
        double high_y = 52;
        double r = 3.0;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, low_y, r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x,high_y,r);
        assertEquals(expectedValidity, actualValidity);
    }

    @Test
    public void borderR() {
        double x = -1;
        double y = 3.0;
        double low_r = 0.4;
        double high_r = 52;
        //радиус физически не может быть меньше или равен 0
        double impossible_r = -52;

        boolean expectedValidity = false;

        boolean actualValidity = Checker.getIsValid(x, y, low_r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, high_r);
        assertEquals(expectedValidity, actualValidity);

        actualValidity = Checker.getIsValid(x, y, impossible_r);
        assertEquals(expectedValidity, actualValidity);
    }
}