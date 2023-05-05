package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.constant.CommonConstant;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.model.dto.user.VcsUserQueryRequest;
import com.ryk.vcsbyrfid.model.entity.VcsCode;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.msgSend.SendMsg;
import com.ryk.vcsbyrfid.service.VcsCodeService;
import com.ryk.vcsbyrfid.mapper.VcsCodeMapper;
import com.ryk.vcsbyrfid.service.VcsUserService;
import com.ryk.vcsbyrfid.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_CODE_TEMPLATE_ID;
import static com.ryk.vcsbyrfid.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author RYKK
* @description 针对表【vcs_code(验证码对照表)】的数据库操作Service实现
* @createDate 2023-04-28 20:47:27
*/
@Service
public class VcsCodeServiceImpl extends ServiceImpl<VcsCodeMapper, VcsCode>
    implements VcsCodeService{

    @Resource
    private VcsUserService vcsUserService;

    @Override
    public Boolean sendVerifyCode(String phone) {
        QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(phone), "phone", phone);
        List<VcsUser> list = vcsUserService.list(queryWrapper);
        if (list.size() == 0) {
            return false;
        }
        VcsUser user = list.get(0);
        Long id = user.getId();
        VcsCode code = new VcsCode();
        Integer codeNum = (int) ((Math.random() * 9 + 1) * 100000);
        code.setId(id);
        code.setCode(codeNum);
        this.save(code);
        SendMsg sendMsg = new SendMsg();
        sendMsg.sendMegToUser(phone, SEND_CODE_TEMPLATE_ID, codeNum.toString(), null, null, null);
        return true;
    }

    @Override
    public Boolean checkVerifyCode(String phone, String codeNum) {
        QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(phone), "phone", phone);
        List<VcsUser> list = vcsUserService.list(queryWrapper);
        if (list.size() == 0) {
            return false;
        }
        VcsUser user = list.get(0);
        Long id = user.getId();
        VcsCode code = this.getById(id);
        Integer rightCode = code.getCode();
        if (rightCode.toString().equals(codeNum)) {
            this.removeById(code);
            return true;
        }
        return false;
    }


}




