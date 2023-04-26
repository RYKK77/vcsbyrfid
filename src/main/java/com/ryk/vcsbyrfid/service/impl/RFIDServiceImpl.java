package com.ryk.vcsbyrfid.service.impl;

import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.RFIDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author RYKK
 * @description
 */
@Service
public class RFIDServiceImpl implements RFIDService {

    @Resource
    private UHFReader uhfReader;

//    public RFIDServiceImpl() {
//        int i = uhfReader.OpenByCom(6, (byte) 5);
//        if (i == 0) {
//            System.out.println("Connect Successfully!");
//        }
//    }
//    //重写finalize方法
//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//        int l = uhfReader.CloseByCom();
//        if (l == 0) {
//            System.out.println("Close Successfully!");
//        }
//    }

    @Override
    public boolean writeRFIDTag(String license) {

        int i = uhfReader.OpenByCom(6, (byte) 5);
        //写当前标签ID,后面加上0000CDCD
        int k = uhfReader.WriteEPC(license + "0000CDCD", "0");
        if (k == 0) {
            System.out.println("WriteEPC Successfully!");
            int l = uhfReader.CloseByCom();
            return true;
        }
        int l = uhfReader.CloseByCom();
        return false;
    }

    @Override
    public String findRFIDTag() {
        int i = uhfReader.OpenByCom(6, (byte) 5);
        String[] inventory = uhfReader.Inventory();
        if (inventory.length != 0) {
            System.out.println("find tagName:" + inventory[0]);
            int l = uhfReader.CloseByCom();
            return inventory[0];
        }
        int l = uhfReader.CloseByCom();
        return null;
    }
}




