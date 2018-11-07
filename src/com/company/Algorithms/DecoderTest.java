package com.company.Algorithms;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DecoderTest {
    private Decoder decoder = new Decoder();

    @DataProvider
    Object[][] data() {
        return new Object[][]{
                {"du debil es"},
                {"Yes HeTerO eM"},
                {"siriUs@ xUy cHuNi"},
                {"TRaKTor"},
                {"YIEieie,,,457852..301"},
                {"Afyan hors arev ara,?!"},
                {"ElenI AnunY EleNN CHI"},
                {"IRaN UxxAKi tenC enQ asUm"},
                {"5465 94"},
                {"Afyan"},
                {"Paron Afyan, Duq Pxik Eq!!!!"},
                {"!@#$%^&*(*&^%$%^&*&:>:>?>"},
                {"110010110"}
        };
    }

    @Test(dataProvider = "data")
    public void decoderTest(String text) {
        System.out.println("text = " + text);
        String decode = decoder.decode(text);
        String encode = decoder.encode(decode);
        System.out.println("decode = " + decode);
        System.out.println("encode = " + encode);
        assertEquals(text, encode);
    }

    @BeforeMethod
    public void before() {
        System.out.println("------------------");
    }
}