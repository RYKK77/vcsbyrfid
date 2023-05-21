package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.constant.CommonConstant;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.mapper.VcsUserMapper;
import com.ryk.vcsbyrfid.model.dto.request.VcsUserQueryRequest;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.model.vo.VcsLoginUserVO;
import com.ryk.vcsbyrfid.model.vo.VcsUserVO;
import com.ryk.vcsbyrfid.utils.SendMsg;
import com.ryk.vcsbyrfid.service.VcsNvehicleService;
import com.ryk.vcsbyrfid.service.VcsUserService;
import com.ryk.vcsbyrfid.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID;

import static com.ryk.vcsbyrfid.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author RYKK
 * @description 针对表【vcs_user】的数据库操作Service实现
 * @createDate 2023-04-22 12:57:24
 */
@Service
@Slf4j
public class VcsUserServiceImpl extends ServiceImpl<VcsUserMapper, VcsUser> implements VcsUserService {

    @Resource
    private VcsNvehicleService vcsNvehicleService;
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "rykk";

    @Override
    public long userRegister(String sid, String userName, Integer role, String phoneNumber, String mail, String password, String checkPassword) {
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
            vcsUser.setUserName(userName);
            vcsUser.setPassword(encryptPassword);
            vcsUser.setMail(mail);
            vcsUser.setPhone(phoneNumber);
            vcsUser.setRole(role);
            boolean saveResult = this.save(vcsUser);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return vcsUser.getId();
        }
    }

    @Override
    public VcsLoginUserVO userLogin(String sid, String password, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(sid, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (sid.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", sid);
        queryWrapper.eq("password", encryptPassword);
        VcsUser user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("request login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    @Override
    public VcsUser getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public VcsUser getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser user = (VcsUser) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(VcsUser user) {
        if (user == null) {
            return false;
        }
        if (user.getRole() == 0 || user.getRole() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public VcsLoginUserVO getLoginUserVO(VcsUser user) {
        if (user == null) {
            return null;
        }
        VcsLoginUserVO loginUserVO = new VcsLoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public VcsUserVO getUserVO(VcsUser user) {
        if (user == null) {
            return null;
        }
        VcsUserVO userVO = new VcsUserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<VcsUserVO> getUserVO(List<VcsUser> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<VcsUser> getQueryWrapper(VcsUserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String sid = userQueryRequest.getSid();
        String mail = userQueryRequest.getMail();
        String phone = userQueryRequest.getPhone();
        Integer role = userQueryRequest.getRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(sid), "sid", sid);
        queryWrapper.eq(StringUtils.isNotBlank(mail), "mail", mail);
        queryWrapper.eq(StringUtils.isNotBlank(phone), "phone", phone);
        queryWrapper.eq(role != null, "role", id);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Boolean sendWarningMsg(String id, String msg) {
        VcsUser user = this.getById(id);
        SendMsg sendMsg = new SendMsg();
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间

        QueryWrapper<VcsNvehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", id);
        List<VcsNvehicle> vcsNvehicleList = vcsNvehicleService.list(queryWrapper);
        if (vcsNvehicleList.size() == 0) {
            return false;
        }
        VcsNvehicle vcsNvehicle = vcsNvehicleList.get(0);
        String carNumber = vcsNvehicle.getCarNumber();

        sendMsg.sendMegToUser(user.getPhone(), SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID, null, sdf.format(date), "西门",carNumber);
        return true;
    }


}




