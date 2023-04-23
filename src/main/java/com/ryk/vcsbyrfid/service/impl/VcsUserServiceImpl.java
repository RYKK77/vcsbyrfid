package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.mapper.VcsUserMapper;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.service.VcsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * @author RYKK
 * @description 针对表【vcs_user】的数据库操作Service实现
 * @createDate 2023-04-22 12:57:24
 */
@Service
public class VcsUserServiceImpl extends ServiceImpl<VcsUserMapper, VcsUser> implements VcsUserService {
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "rykk";

    @Override
    public long userRegister(String sid, String phoneNumber, String mailAccount, String password, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(sid, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (sid.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (sid.intern()) {
            // 账户不能重复
            QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sid", sid);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 插入数据
            VcsUser vcsUser = new VcsUser();
            vcsUser.setSid(sid);
            vcsUser.setPassword(encryptPassword);
            vcsUser.setMail(mailAccount);
            vcsUser.setPhone(phoneNumber);
            boolean saveResult = this.save(vcsUser);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return vcsUser.getId();
        }
    }


}




