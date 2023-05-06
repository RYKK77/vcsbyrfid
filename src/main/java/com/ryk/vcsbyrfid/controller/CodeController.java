package com.ryk.vcsbyrfid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryk.vcsbyrfid.common.BaseResponse;
import com.ryk.vcsbyrfid.common.DeleteRequest;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.common.ResultUtils;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.exception.ThrowUtils;
import com.ryk.vcsbyrfid.model.dto.user.*;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.model.vo.VcsLoginUserVO;
import com.ryk.vcsbyrfid.model.vo.VcsUserVO;
import com.ryk.vcsbyrfid.service.VcsCodeService;
import com.ryk.vcsbyrfid.service.VcsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author ryk
 * @from  
 */
@RestController
@RequestMapping("/verify")
@Slf4j
public class CodeController {

    @Resource
    private VcsCodeService vcsCodeService;

    /**
     * 发送验证码
     * @param phone 电话号码
     * @return 发送结果
     */
    @GetMapping("/code")
    public BaseResponse<String> sendCode(String phone) {
        Boolean result = vcsCodeService.sendVerifyCode(phone);
        if (result == true) {

            return ResultUtils.success("发送成功");
        }
        return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
    }
    @GetMapping("/check")
    public BaseResponse<String> checkCode(String phone, String codeNum) {
        Boolean result = vcsCodeService.checkVerifyCode(phone, codeNum);
        if (result == true) {
            return ResultUtils.success("验证码正确");
        }
        return ResultUtils.error(ErrorCode.CODE_WRONG);
    }


}
