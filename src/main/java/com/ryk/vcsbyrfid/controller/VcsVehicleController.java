package com.ryk.vcsbyrfid.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ryk.vcsbyrfid.common.BaseResponse;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.common.ResultUtils;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.exception.ThrowUtils;
import com.ryk.vcsbyrfid.model.dto.request.*;
import com.ryk.vcsbyrfid.model.dto.respond.VcsRecordQueryRespond;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsRecord;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.model.vo.VcsCarVO;
import com.ryk.vcsbyrfid.model.vo.VcsNvehicleVO;
import com.ryk.vcsbyrfid.service.VcsNvehicleService;
import com.ryk.vcsbyrfid.service.VcsRecordService;
import com.ryk.vcsbyrfid.service.VcsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ryk.vcsbyrfid.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author ryk
 * @from
 */
@RestController
@RequestMapping("/vehicle")
@Slf4j
public class VcsVehicleController {

    @Resource
    private VcsNvehicleService vcsNvehicleService;

    @Resource
    private VcsUserService vcsUserService;

    @Resource
    private VcsRecordService vcsRecordService;

    /**
     * 查找自己的车辆
     *
     * @param request
     * @return
     */
    @GetMapping("/info")
    public BaseResponse<VcsNvehicleVO> vehicleInfo(HttpServletRequest request) {
        VcsNvehicleVO vehicleInfo = vcsNvehicleService.getVehicleInfo(request);
        if (vehicleInfo != null) {
            return ResultUtils.success(vehicleInfo);
        }
        return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
    }

    /**
     * 通过车辆ID查找车辆
     */
    @GetMapping("/infobyid")
    public BaseResponse<VcsNvehicleVO> vehicleInfoById(Long id) {
        VcsRecord record = vcsRecordService.getById(id);
        VcsNvehicleVO vcsNvehicleVO = new VcsNvehicleVO();
        BeanUtils.copyProperties(record, vcsNvehicleVO);
        if (vcsNvehicleVO != null) {
            return ResultUtils.success(vcsNvehicleVO);
        }
        return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
    }

    /**
     * 添加车辆(仅管理员)
     *
     * @param vcsVehicleRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addVehicle(@RequestBody VcsVehicleRequest vcsVehicleRequest, HttpServletRequest request) {
        if (vcsVehicleRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        VcsNvehicle vcsNvehicle = new VcsNvehicle();
        BeanUtils.copyProperties(vcsVehicleRequest, vcsNvehicle);
        boolean result = vcsNvehicleService.save(vcsNvehicle);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(vcsNvehicle.getId());
    }

    /**
     * 修改车辆(仅管理员)
     *
     * @param vcsVehicleUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<String> updateVehicle(@RequestBody VcsVehicleUpdateRequest vcsVehicleUpdateRequest, HttpServletRequest request) {
        if (vcsVehicleUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        VcsNvehicle vcsNvehicle = new VcsNvehicle();
        BeanUtils.copyProperties(vcsVehicleUpdateRequest, vcsNvehicle);
        boolean result = vcsNvehicleService.updateById(vcsNvehicle);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success("修改成功");
    }

    /**
     * 更新车辆正常使用时间
     *
     * @param vcsVehicleTimeRequest
     * @param request
     * @return
     */
    @PostMapping("/update/time")
    public BaseResponse<Boolean> updateVehicleTime(@RequestBody VcsVehicleTimeRequest vcsVehicleTimeRequest, HttpServletRequest request) {
        VcsNvehicle vcsNvehicle = new VcsNvehicle();
        BeanUtils.copyProperties(vcsVehicleTimeRequest, vcsNvehicle);
        Boolean result = vcsNvehicleService.updateVehicleTime(vcsNvehicle, request);
        if (result) {
            return ResultUtils.success(true);
        }
        return ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 更改车辆状态
     *
     * @param vcsVehicleStatusRequest
     * @param request
     * @return
     */
    @PostMapping("/update/status")
    public BaseResponse<Boolean> updateVehicleStatus(@RequestBody VcsVehicleStatusRequest vcsVehicleStatusRequest, HttpServletRequest request) {
        VcsNvehicle vcsNvehicle = new VcsNvehicle();
        BeanUtils.copyProperties(vcsVehicleStatusRequest, vcsNvehicle);
        Boolean result = vcsNvehicleService.updateVehicleStatus(vcsNvehicle, request);

        if (result) {
            return ResultUtils.success(true);
        }
        return ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 分页获取车辆记录
     *
     * @param request
     * @return
     */
    @PostMapping("/records")
    public BaseResponse<VcsRecordQueryRespond> listVehicleRecordByPage(@RequestBody VcsRecordQueryRequest recordQueryRequest,
                                                                       HttpServletRequest request) {
        if (recordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        VcsRecordQueryRespond queryRecords = vcsRecordService.getQueryRecords(recordQueryRequest);
        return ResultUtils.success(queryRecords);
    }

    /**
     * 异常记录消除
     * @param request
     * @return
     */
    @GetMapping("/records/clear")
    public BaseResponse<Boolean> clearWaringRecords(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        VcsUser currentUser = (VcsUser) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long userId = currentUser.getId();

        UpdateWrapper<VcsRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", userId);
        updateWrapper.set("type", 0);
        boolean result = vcsRecordService.update(updateWrapper);
        return ResultUtils.success(result);
    }

    /**
     * 根据用户ID获取车辆信息
     * @param userId 用户ID
     * @param request request
     * @return response
     */
    @GetMapping("/vehicleinfo/{userId}")
    public BaseResponse<?> getUserVehicleInfoById(@PathVariable("userId") String userId, HttpServletRequest request) {
        List<VcsCarVO> vehicleInfoByUserId = vcsNvehicleService.getVehicleInfoByUserId(userId, request);
        return ResultUtils.success(vehicleInfoByUserId);
    }
}
