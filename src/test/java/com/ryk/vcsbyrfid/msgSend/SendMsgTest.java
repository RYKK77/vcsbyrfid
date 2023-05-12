package com.ryk.vcsbyrfid.msgSend;


import com.ryk.vcsbyrfid.utils.SendMsg;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID;

@SpringBootTest
public class SendMsgTest {
    @Test
    void send() {
        SendMsg sendMsg = new SendMsg();
//        sendMsg.sendMegToUser("18682543126", SEND_CODE_TEMPLATE_ID, "123456", null, null,null);

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        sendMsg.sendMegToUser("18682543126", SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID, null, sdf.format(date), "西门","川A-B959B");
    }
}
