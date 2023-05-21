package com.ryk.vcsbyrfid.controller;

import com.ryk.vcsbyrfid.common.BaseResponse;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.common.ResultUtils;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.model.dto.request.VcsRfidAddRequest;
import com.ryk.vcsbyrfid.service.VcsRfidService;
import com.ryk.vcsbyrfid.service.VcsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * RFID接口
 *
 * @author ryk
 * @from  
 */
@RestController
@RequestMapping("/rfid")
@Slf4j
public class VcsRFIDController {
    @Resource
    private VcsRfidService vcsRfidService;

    @Resource
    private VcsUserService vcsUserService;

    /**
     * 生成RFID信息并储存，同时写入标签
     * @param vcsRfidAddRequest
     * @param request
     * @return 标签ID
     */
    @PostMapping("/generate")
    public BaseResponse<Long> generateRfidTag(@RequestBody VcsRfidAddRequest vcsRfidAddRequest, HttpServletRequest request){
        if (vcsRfidAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }
        Long result = vcsRfidService.generateRfidTag(vcsRfidAddRequest, request);
        return ResultUtils.success(result);

    }
}
