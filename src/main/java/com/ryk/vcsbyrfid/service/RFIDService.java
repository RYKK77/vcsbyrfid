package com.ryk.vcsbyrfid.service;



/**
* @author RYKK
* @description 操作RFID标签
*/
public interface RFIDService {
    /**
     * 写车牌信息到标签
     * @param license 长度一定是4的倍数！！！
     * @return
     */
    boolean writeRFIDTag(String license);

    /**
     * 查询标签
     * @return
     */
    String findRFIDTag();
}
