package com.ryk.vcsbyrfid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ryk.vcsbyrfid.model.entity.VcsUser;

/**
* @author RYKK
* @description 针对表【vcs_user】的数据库操作Service
* @createDate 2023-04-22 12:49:04
*/
public interface VcsUserService extends IService<VcsUser> {
    /**
     *
     * @param sid   用户账户
     * @param phoneNumber 电话号码
     * @param mailAccount   邮箱账号
     * @param password  用户密码
     * @param checkPassword 校验密码
     * @return  新用户 id
     */
    long userRegister(String sid, String phoneNumber, String mailAccount, String password, String checkPassword);
}
