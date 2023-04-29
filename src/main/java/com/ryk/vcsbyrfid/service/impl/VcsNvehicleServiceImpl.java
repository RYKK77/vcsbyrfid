package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.mapper.VcsNvehicleMapper;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.model.vo.VcsNvehicleVO;
import com.ryk.vcsbyrfid.service.VcsNvehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ryk.vcsbyrfid.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author RYKK
* @description 针对表【vcs_nvehicle(车辆)】的数据库操作Service实现
* @createDate 2023-04-22 22:36:24
*/
@Service
public class VcsNvehicleServiceImpl extends ServiceImpl<VcsNvehicleMapper, VcsNvehicle> implements VcsNvehicleService{

    @Override
    public VcsNvehicleVO getVehicleInfo(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long userId = currentUser.getId();
        QueryWrapper<VcsNvehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<VcsNvehicle> vcsNvehicleList = this.list(queryWrapper);
        if (vcsNvehicleList.size() == 0) {
            return null;
        }
        VcsNvehicle vcsNvehicle = vcsNvehicleList.get(0);
        VcsNvehicleVO vcsNvehicleVO = new VcsNvehicleVO();
        BeanUtils.copyProperties(vcsNvehicle, vcsNvehicleVO);
        return vcsNvehicleVO;
    }

    @Override
    public Boolean updateVehicleTime(VcsNvehicle vcsNvehicle, HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long userId = currentUser.getId();
        vcsNvehicle.setUserId(userId);
        boolean result = this.updateById(vcsNvehicle);
        return result;

    }

    @Override
    public Boolean updateVehicleStatus(VcsNvehicle vcsNvehicle, HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long userId = currentUser.getId();
        vcsNvehicle.setUserId(userId);
        boolean result = this.updateById(vcsNvehicle);
        return result;
    }
}




