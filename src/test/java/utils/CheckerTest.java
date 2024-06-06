package utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static utils.Checker.checkHit;

public class CheckerTest {
    @Before
    public void setUp() {
        System.out.println("52!");
    }

    @Test
    public void checkIfNotOnTriangle() {
        assertFalse(checkHit(1.0000001,-0.5,3));
    }
}