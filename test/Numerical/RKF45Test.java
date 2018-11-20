package Numerical;

import com.company.Numerical.ODE.Embedded.RKF45;
import com.company.Numerical.ODE.ODE;
import com.company.Numerical.ODE.ODESolver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Math.exp;
import static org.testng.Assert.assertEquals;

public class RKF45Test {
    private ODESolver rkf45 = new RKF45();

    private ODE firstOrder = (x1, y) -> y[0];

    private static double round(double num) {
        return Math.round(num * Math.pow(10, 5)) / Math.pow(10, 5);
    }

    @DataProvider
    public static Object[][] firstOrderData() {
        return new Object[][]{
                {0, 1, 1},
                {0, 0, 1},
                {1, 0, 0},
                {1, 1, 0},
                {0, 12, 8},
                {0, 10, 9}
        };
    }

    @Test(dataProvider = "firstOrderData")
    public void testSolveFirstOrder(double x0, double y0, double x) {
        double result = round(rkf45.solveFirstOrder(x0, y0, x, firstOrder));
        double expected = round(y0 * exp(x - x0));
        System.out.println(expected + " " + result);
        assertEquals(expected, result);
    }
}