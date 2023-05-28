package com.ryk.vcsbyrfid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ryk.vcsbyrfid.model.dto.request.VcsRfidAddRequest;
import com.ryk.vcsbyrfid.model.dto.request.VcsRfidUpdateRequest;
import com.ryk.vcsbyrfid.model.entity.VcsRfid;

import javax.servlet.http.HttpServletRequest;

/**
* @author RYKK
* @description 针对表【vcs_rfid(RFID信息)】的数据库操作Service
* @createDate 2023-04-22 22:45:14
*/
public interface VcsRfidService extends IService<VcsRfid> {

    /**
     * 生成RFID信息并储存，同时写入标签
     * @param vcsRfidAddRequest
     * @param request
     * @return
     */
    public Long generateRfidTag(VcsRfidAddRequest vcsRfidAddRequest, HttpServletRequest request);
    /**
     * 更新RFID信息并储存
     * @param vcsRfidUpdateRequest
     * @param request
     * @return
     */
    public Boolean updateRfidTag(VcsRfidUpdateRequest vcsRfidUpdateRequest, HttpServletRequest request);

}
