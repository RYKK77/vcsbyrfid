package com.ryk.vcsbyrfid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

/**
 * 用户接口
 *
 * @author ryk
 * @from
 */
@RestController
@RequestMapping("/vcsuser")
@Slf4j
public class VcsUserController {

    @Resource
    private VcsUserService vcsUserService;

    @Resource
    private VcsCodeService vcsCodeService;


    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody VcsUserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String sid = userRegisterRequest.getSid();
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String phoneNumber = userRegisterRequest.getPhone();
        String mail = userRegisterRequest.getMail();
        Integer role = userRegisterRequest.getRole();
        if (StringUtils.isAnyBlank(sid, userName, userPassword, checkPassword, phoneNumber, mail)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long result = vcsUserService.userRegister(sid, userName, role, phoneNumber, mail, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<VcsLoginUserVO> userLogin(@RequestBody VcsUserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getSid();
        String userPassword = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        VcsLoginUserVO vcsLoginUserVO = vcsUserService.userLogin(userAccount, userPassword, request);
        log.info("【用户操作】"+"用户："+ vcsLoginUserVO.getUserName()+",学号："+ vcsLoginUserVO.getSid()+"已登录");
        return ResultUtils.success(vcsLoginUserVO);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = vcsUserService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<VcsLoginUserVO> getLoginUser(HttpServletRequest request) {
        VcsUser loginUser = vcsUserService.getLoginUser(request);
        return ResultUtils.success(vcsUserService.getLoginUserVO(loginUser));
    }

    /**
     * 创建用户(仅管理员)
     *
     * @param userAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@RequestBody VcsUserAddRequest userAddRequest, HttpServletRequest request) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        VcsUser user = new VcsUser();
        BeanUtils.copyProperties(userAddRequest, user);
        //默认密码为1234567890
        user.setPassword(DigestUtils.md5DigestAsHex(("rykk" + "1234567890").getBytes()));
        boolean result = vcsUserService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }


    /**
     * 删除用户F
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = vcsUserService.removeById(deleteRequest.getId());
        if (b) {
            return ResultUtils.success(b);
        }
        return ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    /**
     * 更新用户（管理员）
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody VcsUserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        VcsUser user = new VcsUser();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = vcsUserService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id      用户ID
     * @param request
     * @return
     */
    @GetMapping("/accountInfo")
    public BaseResponse<VcsUser> getUserById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        VcsUser user = vcsUserService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<VcsUserVO> getUserVOById(long id, HttpServletRequest request) {
        BaseResponse<VcsUser> response = getUserById(id, request);
        VcsUser user = response.getData();
        return ResultUtils.success(vcsUserService.getUserVO(user));
    }


    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<VcsUser>> listUserByPage(@RequestBody VcsUserQueryRequest userQueryRequest,
                                                      HttpServletRequest request) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<VcsUser> userPage = vcsUserService.page(new Page<>(current, size),
                vcsUserService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest
     * @param request
     * @return
     */
    @PostMapping("/update/my")
    public BaseResponse<Boolean> updateMyUser(@RequestBody VcsUserUpdateMyRequest userUpdateMyRequest,
                                              HttpServletRequest request) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        VcsUser loginUser = vcsUserService.getLoginUser(request);
        VcsUser user = new VcsUser();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = vcsUserService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }


    @GetMapping("/warning")
    public BaseResponse<Boolean> sendWarning(VcsUserWarningRequest userWarningRequest, HttpServletRequest request) {
        String id = userWarningRequest.getId();
        String warningMsg = userWarningRequest.getWarningMsg();
        vcsUserService.sendWarningMsg(id, warningMsg);
        return ResultUtils.success(true);
    }

    @GetMapping("/verify/code")
    public BaseResponse<String> sendCode(String phone) {
        Boolean result = vcsCodeService.sendVerifyCode(phone);
        if (result == true) {
            return ResultUtils.success("发送成功");
        }
        return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
    }

    @GetMapping("/verify/check")
    public BaseResponse<String> checkCode(String phone, String codeNum) {
        Boolean result = vcsCodeService.checkVerifyCode(phone, codeNum);
        if (result == true) {
            return ResultUtils.success("验证码正确");
        }
        return ResultUtils.error(ErrorCode.CODE_WRONG);
    }


    @GetMapping("/forgotPassword")
    public BaseResponse<String> modifyPassword(String phone, String newPassword, String codeNum) {
        Boolean result = vcsCodeService.checkVerifyCode(phone, codeNum);
        if (result == true) {
            QueryWrapper<VcsUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(phone), "phone", phone);
            List<VcsUser> list = vcsUserService.list(queryWrapper);
            if (list.size() == 0) {
                return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            }
            VcsUser user1 = list.get(0);
            Long userID = user1.getId();
            VcsUser user = new VcsUser();
            user.setId(userID);
            user.setPassword(DigestUtils.md5DigestAsHex(("rykk" + newPassword).getBytes()));
            boolean result1 = vcsUserService.updateById(user);
            ThrowUtils.throwIf(!result1, ErrorCode.OPERATION_ERROR);
            return ResultUtils.success("修改成功！请妥善保管密码。");
        }
        return ResultUtils.error(ErrorCode.CODE_WRONG);
    }
}
