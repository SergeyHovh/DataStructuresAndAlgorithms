package Algorithms;

import com.company.Algorithms.Decoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DecoderTest {
    private final Decoder decoder = new Decoder();

    @DataProvider
    Object[][] encryptionTestData() {
        return new String[][]{
//                {"n3sEKLlFLzI"}
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
                {"8589481"},
                {"985812"},
                {"0 123"},
                {"0123"},
                {"01 23"},
                {" 0123"},
                {"1234567898 76543234567"},
                {"999999999"},
                {""},
                {"            "},
//                {DataStructureGenerator.generateTree(6).toString()}
        };
    }

    @Test(dataProvider = "encryptionTestData")
    public void encoderTest(String text) {
        System.out.println("[text = " + text + "]");
        String encode = decoder.encode(text);
        System.out.println("[encode = " + encode + "]");
        String decode = decoder.decode(encode);
        System.out.println("[decode = " + decode + "]");
        assertEquals(decode, text);
    }

    @BeforeMethod
    public void before() {
        System.out.println("------------------");
    }
}