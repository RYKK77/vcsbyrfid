package com.ryk.vcsbyrfid.rfid.UHF;


import com.ryk.vcsbyrfid.utils.rfid.UHF.UHFReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UHFReaderTest {

    @Resource
    private UHFReader uhfReader;

    @Test
    void WriteEPCTest(){
        int i = uhfReader.OpenByCom(6, (byte) 5);
        byte[] psd = new byte[8];
        /*
        byte WordPtr = (byte)(0xff & Integer.parseInt("0",16));
        byte Num =(byte)(0xff & Integer.parseInt("2",16));

        //下面这一句读取有问题，怀疑是动态链接库没对上
        String s = uhfReader.ReadData(inventory[0], WordPtr, Num, (byte) 3, psd);
        */


        //写当前标签ID,后面加上0000CDCD
        int k = uhfReader.WriteEPC("CDAB959B0000CDCD", "0");
        String[] inventory = uhfReader.Inventory();

        System.out.println(inventory[0]);
//        System.out.println(inventory[1]);
        int l = uhfReader.CloseByCom();
        if (i == 0) {
            System.out.println("Connect Successfully!");
        }
        if (k == 0) {
            System.out.println("WriteEPC Successfully!");
        }
        if (l == 0) {
            System.out.println("Close Successfully!");
        }
    }
    @Test
    void findTagTest(){
        int i = uhfReader.OpenByCom(6, (byte) 5);

        String[] inventory = uhfReader.Inventory();
        int l = uhfReader.CloseByCom();
        if (i == 0) {
            System.out.println("Connect Successfully!");
        }
        if (inventory.length != 0) {
            System.out.println("find tagName:" + inventory[0]);
        }
        if (l == 0) {
            System.out.println("Close Successfully!");
        }
    }
}
