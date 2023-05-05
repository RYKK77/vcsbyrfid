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


    @Override
    public boolean writeRFIDTag(String license) {
        String paddedStr = String.format("%1$8s", license).replace(' ', '0');
        uhfReader.setStatus(1);//避免冲突，使设备当前不可读
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //写当前标签ID,后面加上0000CDCD
        int k = uhfReader.WriteEPC(paddedStr + "0000CDCD", "0");
        if (k == 0) {
            System.out.println("WriteEPC Successfully!");
//            uhfReader.Inventory();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            uhfReader.setStatus(0);//恢复可读状态
            return true;
        }
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




