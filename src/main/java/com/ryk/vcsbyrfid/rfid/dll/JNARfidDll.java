package com.ryk.vcsbyrfid.rfid.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNARfidDll extends Library {
    JNARfidDll jnaDll = (JNARfidDll) Native.loadLibrary("UHFReader09", JNARfidDll.class);

    long OpenComPort(long Port, byte[] ComAdr, byte Baud, long[] handle);//V

    long AutoOpenComPort(long Port, byte[] ComAdr, byte Baud, long[] FrmHandle);//V

    long CloseSpecComPort(long Port);//V

    long Inventory_G2(byte[] ConAddr, byte AdrTID, byte LenTID, byte TIDFlag, byte[] EPClenandEPC,
                      long[] Totallen, long[] CardNum, long PortHandle);//V

    long SetPowerDbm(byte[] ConAddr, byte[] powerDbm, long PortHandle);//V

    long SetRegion(byte[] ConAddr, byte dmaxfre, byte dminfre, long PortHandle);//V

    long SetBaudRate(byte[] ConAddr, byte[] baud, long PortHandle);//V

    long ReadCard_G2(byte[] comAddr, byte[] EPC, byte Mem, byte WordPtr, byte Num, byte[] Password,
                    byte[] Data, byte EPClength, byte[] Errorcode, long PortHandle);//V

    long WriteCard_G2(byte[] comAddr, byte[] EPC, byte Mem, byte WordPtr, byte Writedatalen, byte[] Wdt,
                     byte[] Password, long WrittemDataNum, byte EPClength, byte[] Errorcode, long PortHandle);//V

    long WriteEPC_G2(byte[] ComAdr, byte[] Password, byte[] WriteEPC, byte WriteEPClen, byte[] errorcode, long FrmHandle);//V
}
