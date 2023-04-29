package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.constant.CommonConstant;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.mapper.VcsRecordMapper;
import com.ryk.vcsbyrfid.model.dto.user.VcsRecordQueryRequest;
import com.ryk.vcsbyrfid.model.entity.VcsRecord;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.service.VcsRecordService;
import com.ryk.vcsbyrfid.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.ryk.vcsbyrfid.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author RYKK
* @description 针对表【vcs_record(记录)】的数据库操作Service实现
* @createDate 2023-04-22 22:38:17
*/
@Service
public class VcsRecordServiceImpl extends ServiceImpl<VcsRecordMapper, VcsRecord> implements VcsRecordService{

    @Override
    public QueryWrapper<VcsRecord> getQueryWrapper(VcsRecordQueryRequest recordQueryRequest) {
        if (recordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Integer type = recordQueryRequest.getType();
        Date createdTime = recordQueryRequest.getCreatedTime();
        Long deviceId = recordQueryRequest.getDeviceId();
        Long userId = recordQueryRequest.getUserId();
        Long nvehicleId = recordQueryRequest.getNvehicleId();
        String sortField = recordQueryRequest.getSortField();
        String sortOrder = recordQueryRequest.getSortOrder();
        QueryWrapper<VcsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.eq(createdTime != null, "createdTime", createdTime);
        queryWrapper.eq(deviceId != null, "deviceId", deviceId);
        queryWrapper.eq(nvehicleId != null, "nvehicleId", nvehicleId);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}




