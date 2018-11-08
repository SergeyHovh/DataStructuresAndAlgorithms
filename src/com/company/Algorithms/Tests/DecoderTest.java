package com.company.Algorithms.Tests;

import com.company.Algorithms.Decoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DecoderTest {
    private Decoder decoder = new Decoder();

    @DataProvider
    Object[][] data() {
        return new Object[][]{
                {"hello world"},
                {"HELp Me !!@ansdnaskd"},
                {"ZXCvbnm,./><MNBVCFyjmKjhvGH"},
                {"TRaKTor"},
                {"YIEieie,,,457852..301"},
                {"is this the real life?\nIS THIS JUST FANTASY?"},
                {"pxiknerov pxik"},
                {"IRaN UxxAKi tenC enQ asUm"},
                {"5465 94"},
                {"Afyan"},
                {"Paron Afyan, Duq Pxik Eq!!!!"},
                {"!@#$%^&*(*&^%$%^&*&:>:>?>"},
                {"1"},
                {"10"},
                {"101"},
                {"1010"},
                {"10101"},
                {"858481"},
                {"985812"}
        };
    }

    @Test(dataProvider = "data")
    public void decoderTest(String text) {
        System.out.println("[text = " + text + "]");
        String decode = decoder.decode(text);
        System.out.println("[decode = " + decode + "]");
        String encode = decoder.encode(decode);
        System.out.println("[encode = " + encode + "]");
        assertEquals(encode, text);
    }

    @BeforeMethod
    public void before() {
        System.out.println("------------------");
    }
}