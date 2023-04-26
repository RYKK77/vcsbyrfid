package com.ryk.vcsbyrfid.rfid.UHF;

import com.ryk.vcsbyrfid.rfid.dll.JNARfidDll;
import org.springframework.stereotype.Service;

@Service
public class UHFReader {
    private byte[] ComAddr = new byte[1];//读写器地址
    public int[] FrmHandle = new int[1];//读写器句柄
    int Recv = 0;
    int fComAddr = 0;
    public int[] CardNum = new int[1];

    //打开设备;ComPort:串口号，ComAddr：读写器地址;baudRate：波特率;
    public int OpenByCom(int ComPort, byte baudRate) {
        byte[] comm = new byte[1];
        comm[0] = (byte) 255;
        int[] Handle = new int[1];
        int Recv1 = JNARfidDll.jnaDll.OpenComPort(ComPort, comm, baudRate, Handle);
        if (Recv1 == 0) {
            ComAddr = comm;
            FrmHandle = Handle;
        } else {
            fComAddr = 255;
            FrmHandle[0] = -1;
        }
        return Recv1;
    }

    //关闭设备,FrmHandle设备句柄;
    public int CloseByCom() {
        return JNARfidDll.jnaDll.CloseSpecComPort(FrmHandle[0]);
    }

    public String byteToString(byte[] b) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < b.length; i++) {
            String temp = Integer.toHexString(b[i] & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
        }
        return sb.toString().toUpperCase();
    }

    public byte[] stringToByte(String str) {
        byte[] b = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            b[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
        }
        return b;
    }

    //询查EPC标签
    public String[] Inventory() {
        byte[] StrBuff = new byte[5000];
        int[] Totallen = new int[1];//接收输出的EPClenand 字节数量
        CardNum[0] = 0;//接收输出的EPC的总数量
        byte AdrTID = 0;
        byte LenTID = 6;
        byte TIDFlag = 0;
        byte Target = 0;
        byte InAnt = (byte) 0x80;
        byte Scantime = 10;
        byte FastFlag = 1;
        byte[] UInAnt = new byte[1];
        int[] UTotallen = new int[1];
        byte[] MaskAdr = new byte[2];
        MaskAdr[0] = 0;
        byte[] MaskData = new byte[100];
        MaskData[0] = 0;
        byte MaskFlag = 0;
        //如果询查TID,TIDFlag变量是1,再设置AdrTID和LenTID
        Recv = JNARfidDll.jnaDll.Inventory_G2(ComAddr, AdrTID, LenTID, TIDFlag, StrBuff, UTotallen, CardNum, FrmHandle[0]);
        if ((Recv == 1) | (Recv == 2) | (Recv == 3) | (Recv == 4))//代表已查找结束，
        {
            if (CardNum[0] == 0) return null;
            String[] EPC = new String[(int) CardNum[0]];
            int m = 0;
            for (int index = 0; index < CardNum[0]; index++) {
                int epclen = StrBuff[m++] & 255;
                String EPCstr = "";
                byte[] epc = new byte[epclen];
                for (int n = 0; n < epclen; n++) {
                    byte bbt = StrBuff[m++];
                    epc[n] = bbt;
                    String hex = Integer.toHexString(bbt & 255);
                    if (hex.length() == 1) {
                        hex = "0" + hex;
                    }
                    EPCstr += hex;
                }
                int rssi = StrBuff[m++];
                EPC[index] = EPCstr.toUpperCase();
                //System.out.println(EPCstr.toUpperCase());
            }
            if (CardNum[0] > 0)
                return EPC;
        }
        return null;
    }

    //设置功率,Power功率
    public int SetPower(int Power) {
        byte[] power = new byte[1];
        power[0] = (byte) Power;
        Recv = JNARfidDll.jnaDll.SetPowerDbm(ComAddr, power, FrmHandle[0]);
        return Recv;
    }

    //设置读写器工作频率
    public int SetRegion(int band) {
        int maxfr = ((band & 0x0c) << 4);
        int minfr = (((band & 3) << 6));
        byte[] MaxFres = new byte[1];
        MaxFres[0] = (byte) maxfr;
        byte[] MixFres = new byte[1];
        MixFres[0] = (byte) minfr;

        return JNARfidDll.jnaDll.SetRegion(ComAddr, (byte) maxfr, (byte) minfr, FrmHandle[0]);
    }

    //设置读写器波特率
    public int SetBaudRate(byte BaudRate) {
        byte[] baudRate = new byte[1];
        baudRate[0] = BaudRate;
        return JNARfidDll.jnaDll.SetBaudRate(ComAddr, baudRate, FrmHandle[0]);
    }

    //读数据，EPC号，WordPtr读取地址，Num读取长度，Mem读取区域，Psd访问密码
    public String ReadData(String EPC, byte WordPtr, byte Num, byte Mem, byte[] Psd) {
        byte[] errorcode = new byte[1];
        byte ENum = 0;
        byte[] Nums = new byte[1];
        Nums[0] = Num;
        byte[] maskadr = new byte[1];
        maskadr[0] = 0;
        byte[] maskLen = new byte[1];
        maskLen[0] = 0;
        byte[] MaskData = new byte[1];
        MaskData[0] = 0;
        int len = EPC.length() / 2;
        byte[] epc_arr = new byte[len];
        epc_arr = stringToByte(EPC);
        byte[] Edata = new byte[320];
        byte[] epcLen = new byte[1];
        epcLen[0] = (byte) len;
        Recv = JNARfidDll.jnaDll.ReadCard_G2(ComAddr, epc_arr, Mem, WordPtr, Num, Psd, Edata,
                (byte) epc_arr.length, errorcode, FrmHandle[0]);
        if (Recv == 0) {
            byte[] data = new byte[Num * 2];
            for (int m = 0; m < Num * 2; m++) {
                data[m] = (byte) Edata[m];
            }
            return byteToString(data);
        } else
            return "";
    }

    //写数据,EPC号，WordPtr写入地址，Num写入长度，Data写入数据,Mem写入区域，Psd访问密码
    public int WriteData(String EPC, byte WordPtr, byte Num, byte[] Data, byte Mem, byte[] Psd) {
        int len = EPC.length() / 2;
        byte[] epc_arr = new byte[len];
        epc_arr = stringToByte(EPC);
        int wnum = Num * 2;
        byte[] WrittenDataNum = new byte[1];
        byte[] errorcode = new byte[1];
        byte ENum = 0;
        byte[] maskadr = new byte[1];
        maskadr[0] = 0;
        byte[] MaskData = new byte[1];
        MaskData[0] = 0;
        byte Writedatalen = (byte) Data.length;//X
        int WrittemDataNum = 0;//X
        Recv = JNARfidDll.jnaDll.WriteCard_G2(ComAddr, epc_arr, Mem, WordPtr, Writedatalen, Data,
                Psd, WrittemDataNum, (byte) epc_arr.length, errorcode, FrmHandle[0]);
        return Recv;
    }

    //写数据,EPC号，，Psd访问密码
    public int WriteEPC(String EPC, String Psd) {
//    	//计算PC值(用于控制读取显示EPC长度)
//    	long m, n;
//        n = EPC.length();
//        if (n % 4 == 0)
//        {
//            m = n / 4;
//            m = (m & 0x3F) << 3;
//            EPC = Integer.toHexString(m)+"00"+EPC;
//            //EPC = Convert.ToString(m, 16).PadLeft(2, '0') + "00";
//        }
        int result = 0;
        byte len = (byte) (EPC.length() / 4);//EPC字长度
        if (len % 2 != 0)//保证len为偶数
            len++;
        if (len < 2)
            len = 2;
        if (len > 30)
            len = 30;
        byte[] epc_arr = new byte[len];
        epc_arr = stringToByte(EPC);
        byte[] Psds = stringToByte(Psd);
        byte[] errorcode = new byte[1];
        Recv = JNARfidDll.jnaDll.WriteEPC_G2(ComAddr, Psds, epc_arr, len, errorcode, FrmHandle[0]);
        result = Recv;
        return result;
    }
}
