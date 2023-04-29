package com.ryk.vcsbyrfid.msgSend;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMsgTest {
    @Test
    void send() {
        SendMsg sendMsg = new SendMsg();
        sendMsg.sendMegToUser("18682543126","123456");
    }
}
