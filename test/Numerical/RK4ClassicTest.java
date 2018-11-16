package Numerical;

import com.company.Numerical.ODE;
import com.company.Numerical.RK4Classic;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Math.exp;
import static org.testng.Assert.assertEquals;

public class RK4ClassicTest {
    private RK4Classic rk4Classic = new RK4Classic();

    @DataProvider
    public static Object[][] firstOrderData() {
        return new Object[][]{
                {0, 1, 1},
                {0, 0, 1},
                {1, 0, 0},
                {1, 1, 0},
                {3, 12, 8},
                {0, 10, 9}
        };
    }

    private static double round(double num) {
        return Math.round(num * Math.pow(10, 5)) / Math.pow(10, 5);
    }

    @Test(dataProvider = "firstOrderData")
    public void testSolveFirstOrder(double x0, double y0, double x) {
        ODE firstOrder = (x1, y) -> y[0];
        double result = round(rk4Classic.solveFirstOrder(x0, y0, x, firstOrder));
        double expected = round(y0 * exp(x - x0));
        System.out.println(expected + " " + result);
        assertEquals(expected, result);
    }
}