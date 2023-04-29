package com.ryk.vcsbyrfid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryk.vcsbyrfid.model.dto.user.VcsUserQueryRequest;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.model.vo.VcsLoginUserVO;
import com.ryk.vcsbyrfid.model.vo.VcsUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @param mail   邮箱账号
     * @param password  用户密码
     * @param checkPassword 校验密码
     * @return  新用户 id
     */
    long userRegister(String sid, String userName, Integer role, String phoneNumber, String mail, String password, String checkPassword);


    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    VcsLoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);



    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    VcsUser getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    VcsUser getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(VcsUser user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    VcsLoginUserVO getLoginUserVO(VcsUser user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    VcsUserVO getUserVO(VcsUser user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<VcsUserVO> getUserVO(List<VcsUser> userList);


    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<VcsUser> getQueryWrapper(VcsUserQueryRequest userQueryRequest);


    /**
     * 发送预警信息
     * @param id
     * @param msg
     * @return
     */
    Boolean sendWarningMsg(String id, String msg);

}
