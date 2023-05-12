package com.ryk.vcsbyrfid.licenseCatch;


import com.ryk.vcsbyrfid.utils.LicensePlateRecognition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class LicensePlateRecognitionTest {

    @Test
    void testID() throws IOException {
        LicensePlateRecognition licensePlateRecognition1 = new LicensePlateRecognition();
        licensePlateRecognition1.getLicense();

//        System.out.println("车牌信息:"+license.getData().getNumber() + " " + license.getData().getColor());


    }
}
