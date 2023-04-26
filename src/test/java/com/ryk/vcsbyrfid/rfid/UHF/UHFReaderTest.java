package com.ryk.vcsbyrfid.rfid.UHF;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UHFReaderTest {

    @Resource
    private UHFReader uhfReader;

    @Test
    void openCom(){
        int i = uhfReader.OpenByCom(6, (byte) 5);
        String[] inventory = uhfReader.Inventory();
        byte[] psd = new byte[8];
        byte WordPtr = (byte)(0xff & Integer.parseInt("0",16));
        byte Num =(byte)(0xff & Integer.parseInt("2",16));

        //下面这一句读取有问题，怀疑是动态链接库没对上
        //String s = uhfReader.ReadData(inventory[0], WordPtr, Num, (byte) 3, psd);
        int l = uhfReader.CloseByCom();
        if (i == 0) {
            System.out.println("Connect Successfully!");
        }
        if (l == 0) {
            System.out.println("Close Successfully!");
        }
        System.out.println(inventory[0]);
        //System.out.println(s);


    }
}
