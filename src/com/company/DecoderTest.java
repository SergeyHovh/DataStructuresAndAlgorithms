package com.company;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DecoderTest {
    Decoder decoder = new Decoder();

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
                {"IRaN UxxAKi tenC enQ asUm"}
        };
    }

    @Test(dataProvider = "data")
    public void decoderTest(String text) {
        System.out.println(text);
        String decode = decoder.decode(text);
        String encode = decoder.encode(decode);
        System.out.println(decode);
        System.out.println(encode);
        assertEquals(text, encode);
    }
}