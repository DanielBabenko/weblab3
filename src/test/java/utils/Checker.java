package utils;

public class Checker {

    static boolean isValid;

    public static boolean checkHit(double x, double y, double r) {

        if (x > -5 && x < 3 && y > -5 && y < 3 && r > 1 && r < 4) {

            final boolean firstArea = (x <= 0 && y >= 0 && x * x + y * y <= (r * r));
            final boolean secondArea = (x <= 0 && y <= 0 && x >= -r / 2 && y >= -r);
            final boolean thirdArea = (x >= 0 && y <= 0 && y >= x - r / 2);

            final boolean result = firstArea || secondArea || thirdArea;

            return result;
        } else {
            isValid = false;
            return false;
        }
    }
}