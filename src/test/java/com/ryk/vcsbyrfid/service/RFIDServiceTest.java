package com.ryk.vcsbyrfid.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
public class RFIDServiceTest {

    @Resource
    private RFIDService rfidService;

    @Test
    void test(){
        String rfidTag = rfidService.findRFIDTag();
        System.out.println(rfidTag);
    }

}
