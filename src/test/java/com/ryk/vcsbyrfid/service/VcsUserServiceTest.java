package com.ryk.vcsbyrfid.service;

import org.junit.jupiter.api.Assertions;
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
public class VcsUserServiceTest {

    @Resource
    private VcsUserService vcsUserService;

    @Test
    void userRegister() {
        String userAccount = "rykk77";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String phoneNum = "18888888888";
        String mailAccount = "XXXX@163.com";
        long result = vcsUserService.userRegister(userAccount, phoneNum, mailAccount, userPassword, checkPassword);
        System.out.println(result);
    }
}
