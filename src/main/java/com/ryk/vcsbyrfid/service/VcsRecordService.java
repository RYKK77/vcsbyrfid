package com.ryk.vcsbyrfid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryk.vcsbyrfid.model.dto.request.VcsRecordQueryRequest;
import com.ryk.vcsbyrfid.model.dto.respond.VcsRecordQueryRespond;
import com.ryk.vcsbyrfid.model.entity.VcsRecord;

/**
 * @author RYKK
 * @description 针对表【vcs_record(记录)】的数据库操作Service
 * @createDate 2023-04-22 22:38:17
 */
public interface VcsRecordService extends IService<VcsRecord> {
    /**
     * 获取查询条件
     */
    QueryWrapper<VcsRecord> getQueryWrapper(VcsRecordQueryRequest recordQueryRequest);

    VcsRecordQueryRespond getQueryRecords(VcsRecordQueryRequest recordQueryRequest);


}
