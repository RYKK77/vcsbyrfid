package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.exception.ThrowUtils;
import com.ryk.vcsbyrfid.model.dto.user.VcsRfidAddRequest;
import com.ryk.vcsbyrfid.model.entity.VcsRfid;
import com.ryk.vcsbyrfid.service.VcsRfidService;
import com.ryk.vcsbyrfid.mapper.VcsRfidMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
* @author RYKK
* @description 针对表【vcs_rfid(RFID信息)】的数据库操作Service实现
* @createDate 2023-04-22 22:45:14
*/
@Service
public class VcsRfidServiceImpl extends ServiceImpl<VcsRfidMapper, VcsRfid>
    implements VcsRfidService{
    @Resource
    public RFIDServiceImpl rfidService;

    @Override
    public Long generateRfidTag(VcsRfidAddRequest vcsRfidAddRequest, HttpServletRequest request) {
        Long nvehicleId = vcsRfidAddRequest.getNvehicleId();
        Date validDate = vcsRfidAddRequest.getValidDate();
        Long userId = vcsRfidAddRequest.getUserId();
        VcsRfid vcsRfid = new VcsRfid();
        vcsRfid.setNvehicleId(nvehicleId);
        vcsRfid.setValidDate(validDate);
        vcsRfid.setUserId(userId);
        boolean result = this.save(vcsRfid);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        rfidService.writeRFIDTag(nvehicleId.toString());
        return vcsRfid.getId();
    }
}




