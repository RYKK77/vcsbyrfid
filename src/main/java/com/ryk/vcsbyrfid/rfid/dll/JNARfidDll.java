package com.ryk.vcsbyrfid.rfid.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;


public interface JNARfidDll extends Library {
    JNARfidDll jnaDll = (JNARfidDll)Native.loadLibrary("UHFReader09", JNARfidDll.class);

    int OpenComPort(int Port, byte[] ComAdr, byte Baud, int[] handle);//V

    int AutoOpenComPort(int[] Port, byte[] ComAdr, byte Baud, int[] FrmHandle);//V

    int CloseSpecComPort(int Port);//V

    int Inventory_G2(byte[] ConAddr, byte AdrTID, byte LenTID, byte TIDFlag, byte[] EPClenandEPC,
                      int[] Totallen, int[] CardNum, int PortHandle);//V

    int SetPowerDbm(byte[] ConAddr, byte[] powerDbm, int PortHandle);//V

    int SetRegion(byte[] ConAddr, byte dmaxfre, byte dminfre, int PortHandle);//V

    int SetBaudRate(byte[] ConAddr, byte[] baud, int PortHandle);//V

    int ReadCard_G2(byte[] comAddr, byte[] EPC, byte Mem, byte WordPtr, byte Num, byte[] Password,
                    byte[] Data, byte EPClength, byte[] Errorcode, int PortHandle);//V

    int WriteCard_G2(byte[] comAddr, byte[] EPC, byte Mem, byte WordPtr, byte Writedatalen, byte[] Wdt,
                     byte[] Password, int WrittemDataNum, byte EPClength, byte[] Errorcode, int PortHandle);//V

    int WriteEPC_G2(byte[] ComAdr, byte[] Password, byte[] WriteEPC, byte WriteEPClen, byte[] errorcode, int FrmHandle);//V
}
