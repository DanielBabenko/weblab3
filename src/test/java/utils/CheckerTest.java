package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckerTest {
    @Test
    public void validHitTest() {
        double x = 3.0;
        double y = 2.0;
        double r = 2.0;

        boolean expectedHit = false;
        boolean actualHit = Checker.checkHit(x, y, r);

        assertEquals(expectedHit, actualHit);
    }

    @Test
    public void invalidRadiusTest() {
        double x = 3.0;
        double y = 2.0;
        double r = 3.999999999999999999999999999999999;

        boolean expectedHit = false;
        boolean actualHit = Checker.checkHit(x, y, r);

        assertEquals(expectedHit, actualHit);
    }
}