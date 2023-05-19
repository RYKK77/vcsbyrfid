package com.ryk.vcsbyrfid.service;

import com.ryk.vcsbyrfid.model.entity.VcsCode;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author RYKK
* @description 针对表【vcs_code(验证码对照表)】的数据库操作Service
* @createDate 2023-04-28 20:47:27
*/
public interface VcsCodeService extends IService<VcsCode> {

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    public Boolean sendVerifyCode(String phone);

    /**
     * 检验验证码
     * @param phone 手机号
     * @param codeNum 验证码
     * @return 结果
     */
    public Boolean checkVerifyCode(String phone, String codeNum);
}
